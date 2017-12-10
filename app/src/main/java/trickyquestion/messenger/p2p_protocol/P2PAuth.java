package trickyquestion.messenger.p2p_protocol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.NetworkStateChanged;
import trickyquestion.messenger.network.SocketClient;
import trickyquestion.messenger.network.SocketServer;
import trickyquestion.messenger.p2p_protocol.events.AuthConfirmed;
import trickyquestion.messenger.p2p_protocol.events.AuthRejected;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.key_gen.ClientSide;
import trickyquestion.messenger.p2p_protocol.key_gen.ServerSide;
import trickyquestion.messenger.util.preference.NetworkPreference;
import trickyquestion.messenger.util.string_helper.FixedString;

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
        private String genAuthConMsg(String dhPubKey, UUID my) {
            return "P2PProtocol:AUTH:CON:" + dhPubKey + ":" + my.toString() + ":P2Protocol";//21
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
                ClientSide clientSide = new ClientSide();
                String serverPubKey = FixedString.toDynamicSize(packetParts[4], '$');
                String clientPubKey = FixedString.fill(
                        clientSide.FirstPhase(serverPubKey),
                        '$',
                        serviceCfg.getAuthKeyBitSize()
                );
                String authConPacket = genAuthConMsg(clientPubKey,host.getID());
                socket.getOutputStream().write(authConPacket.getBytes());
                EventBus.getDefault().post(
                        new AuthConfirmed(
                                new P2PFriend(
                                        targetUser.getName(),
                                        targetUser.getID(),
                                        targetUser.getNetworkAddress(),
                                        clientSide.getCipherKey())
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
        @Contract(pure = true)
        private String genAuthReqMsg(String dhPubKey, UUID my) {
            return "P2PProtocol:AUTH:REQ:" + dhPubKey + ":" + my.toString() + ":P2Protocol";//21
        }

        @Override
        public void run() {
            try {
                authProcedureInitialized.acquire();
                ServerSide serverSide = new ServerSide(serviceCfg.getAuthKeyBitSize());
                String key = serverSide.FirstPhase();
                SocketClient socketClient = new SocketClient(Network.IPAddress(serviceContext),serviceCfg.getAuthPort());
                String authPhase1 = genAuthReqMsg(FixedString.fill(key, '$', serviceCfg.getAuthKeyBitSize()),host.getID());
                socketClient.SendData(authPhase1);
                String packet = socketClient.ReceiveData(serviceCfg.getAuthTimeOut(),2048);
                if(packet == null) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                String[] packetParts = packet.split(":");
                if (packetParts.length != 6) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                if (!(packetParts[0].equals("P2PProtocol") &&
                        packetParts[1].equals("AUTH") &&
                        packetParts[2].equals("REQ") &&
                        packetParts[3].equals(user.getID().toString()))) {
                    EventBus.getDefault().post(new AuthRejected(user));
                    socketClient.close();
                    return;
                }
                String clientPubKey = FixedString.toDynamicSize(packetParts[4], '$');
                serverSide.SecondPhase(clientPubKey);
                EventBus.getDefault().post(
                        new AuthConfirmed(
                                new P2PFriend(
                                        user.getName(),
                                        user.getID(),
                                        user.getNetworkAddress(),
                                        serverSide.getCipherKey())));
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
