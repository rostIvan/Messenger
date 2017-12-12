package trickyquestion.messenger.p2p_protocol;

import android.content.Context;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.SocketClient;
import trickyquestion.messenger.network.SocketServer;
import trickyquestion.messenger.p2p_protocol.events.AuthConfirmed;
import trickyquestion.messenger.p2p_protocol.events.AuthRejected;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.preference.NetworkPreference;

/**
 * Created by Zen on 28.11.2017.
 */

public class P2PAuth {
    private NetworkPreference serviceCfg;
    private Context serviceContext;
    private P2PNetwork network;
    private IHost host;

    private Thread AuthListenerThread;
    private Thread AuthThread;

    private SocketServer AuthServer;

    private volatile Semaphore authProcedureInitialized = new Semaphore(1);

    class AuthParser implements SocketServer.ISocketListener{
        volatile Semaphore authMutex = new Semaphore(1);
        volatile Boolean authConfirmed = false;

        public void onEvent(AuthConfirmed e){
            authConfirmed = true;
            authMutex.release();
        }

        public void onEvent(AuthRejected e){
            authConfirmed = false;
            authMutex.release();
        }

        @NonNull
        @Contract(pure = true)
        private String genAuthConMsg(UUID my) {
            return "P2PProtocol:AUTH:CONFIRM:" + my.toString() + ":P2Protocol";
        }

        @Override
        public void proceed(String str, Socket socket) {
            String[] packetParts = str.split(":");
            if (packetParts.length != 6) {
                return;
            }
            if (!(packetParts[0].equals("P2PProtocol") &&
                    packetParts[1].equals("AUTH") &&
                    packetParts[2].equals("REQ"))) {
                return;
            }
            List<IUser> users = network.getUsers();
            UUID targetID = UUID.fromString(packetParts[3]);
            IUser targetUser = null;
            for(IUser user : users){
                if(user.getID().equals(targetID)){
                    targetUser = user;
                    break;
                }
            }
            if(targetUser==null) {
                return;
            }
            EventBus.getDefault().post(new trickyquestion.messenger.p2p_protocol.events.AuthRequest(targetUser));
            authMutex.tryAcquire((int)(serviceCfg.getAuthTimeOut()*0.9));
            authMutex.release();
            try{
                if(!authConfirmed) {
                    String reject = "P2PProtocol:AUTH:REJECT:"+host.getID().toString()+":AUTH:P2PProtocol";
                        socket.getOutputStream().write(reject.getBytes());
                    return;
                }
                socket.getOutputStream().write(genAuthConMsg(host.getID()).getBytes());
                EventBus.getDefault().post(
                        new AuthConfirmed(
                                new P2PFriend(
                                        targetUser.getName(),
                                        targetUser.getID(),
                                        targetUser.getNetworkAddress())
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public P2PAuth(Context serviceContext, NetworkPreference serviceCfg, P2PNetwork network, IHost host){
        this.serviceCfg = serviceCfg;
        this.serviceContext = serviceContext;
        this.network = network;
        this.host = host;
        this.AuthServer = new SocketServer(serviceCfg.getAuthPort());
        this.AuthServer.registerListener(new AuthParser());
        this.AuthListenerThread = new Thread(AuthServer);
    }

    public class AuthRequest implements Runnable {
        private IUser user;

        AuthRequest(IUser user) {
            this.user = user;
        }

        @NonNull
        private String genAuthPhase1Packet(UUID my, UUID target){
            return "P2PProtocol:AUTH:REQ:" + my.toString() + ":" + target.toString() + ":P2Protocol";
        }

        @Override
        public void run() {
            try {
                authProcedureInitialized.acquire();
                SocketClient socketClient = new SocketClient(Network.IPAddress(serviceContext),serviceCfg.getAuthPort());
                socketClient.SendData(genAuthPhase1Packet(host.getID(),user.getID()));
                String packet = socketClient.ReceiveData(serviceCfg.getAuthTimeOut(),2048);
                if(packet == null) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                String[] packetParts = packet.split(":");
                if (packetParts.length != 5) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                if (!(packetParts[0].equals("P2PProtocol") &&
                        packetParts[1].equals("AUTH") &&
                        packetParts[2].equals("CONFIRM") &&
                        packetParts[3].equals(user.getID().toString()))) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                EventBus.getDefault().post(
                        new AuthConfirmed(
                                new P2PFriend(
                                        user.getName(),
                                        user.getID(),
                                        user.getNetworkAddress()
                                )
                        )
                );
                socketClient.close();
            } catch (InterruptedException e) {
                EventBus.getDefault().post(new AuthRejected(user));
                e.printStackTrace();
            }
            finally {
                authProcedureInitialized.release();
            }
        }
    }

    public void NewAuthReq(IUser target){
        try {
            authProcedureInitialized.acquire();
            AuthThread = new Thread(new AuthRequest(target));
            AuthThread.start();
            authProcedureInitialized.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Stop(){
        AuthListenerThread.stop();
    }
}
