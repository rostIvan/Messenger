package trickyquestion.messenger.p2p_protocol.modules;

import android.content.Context;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.socket.SocketClient;
import trickyquestion.messenger.network.socket.SocketServer;
import trickyquestion.messenger.p2p_protocol.objects.OFriend;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendConfirmed;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendRejected;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendRequest;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.android.preference.NetworkPreference;

/**
 * Created by Zen on 28.11.2017.
 */

public class P2PAddFriends {
    private NetworkPreference serviceCfg;
    private Context serviceContext;
    private P2PNetwork network;
    private IHost host;

    private Thread AddFriendListenerThread;

    private volatile Semaphore addFriendProcedureInitialized = new Semaphore(1);

    class AddFriendListener implements SocketServer.ISocketListener{
        volatile Semaphore addFriendMutex = new Semaphore(1);
        volatile Boolean authConfirmed = false;

        public void onEvent(EAddFriendConfirmed e){
            authConfirmed = true;
            addFriendMutex.release();
        }

        public void onEvent(EAddFriendRejected e){
            authConfirmed = false;
            addFriendMutex.release();
        }

        @NotNull
        String genConfirmPacket(@NotNull UUID target){
            return "P2PProtocol:AUTH:CONFIRM:" + host.getID() + ":" + target.toString() +":" + "P2PProtocol";
        }

        @NotNull
        String genRejectPacket(@NotNull UUID target){
            return "P2PProtocol:AUTH:REJECT:" + host.getID() + ":" + target.toString() +":" + "P2PProtocol";
        }

        IUser FindUser(UUID targetID){
            List<IUser> users = network.getUsers();
            IUser targetUser = null;
            for (IUser user : users) {
                if (user.getID().equals(targetID)) {
                    return user;
                }
            }
            return null;
        }

        @Override
        public void proceed(String str, Socket socket) {
            String[] parts = str.split(":");
            if (parts.length == 6)
                if (parts[0].equals("P2PProtocol") &&
                        parts[1].equals("AUTH") &&
                        parts[2].equals("REQ") &&
                        parts[4].equals(host.getID().toString())) {
                    IUser targetUser = FindUser(UUID.fromString(parts[2]));
                    if(targetUser==null) return;
                    EventBus.getDefault().post(new EAddFriendRequest(targetUser));
                    addFriendMutex.tryAcquire((int) (serviceCfg.getAuthTimeOut() * 0.9));
                    addFriendMutex.release();
                    if (authConfirmed) {
                        try {
                            socket.getOutputStream().write(genConfirmPacket(UUID.fromString(parts[4])).getBytes());
                            EventBus.getDefault().post(
                                    new EAddFriendConfirmed(
                                            new OFriend(
                                                    targetUser.getName(),
                                                    targetUser.getID(),
                                                    targetUser.getNetworkAddress())
                                    )
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            socket.getOutputStream().write(genRejectPacket(UUID.fromString(parts[4])).getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    public P2PAddFriends(Context serviceContext, NetworkPreference serviceCfg, P2PNetwork network, IHost host){
        this.serviceCfg = serviceCfg;
        this.serviceContext = serviceContext;
        this.network = network;
        this.host = host;
        SocketServer addFriendServer = new SocketServer(serviceCfg.getAuthPort());
        addFriendServer.registerListener(new AddFriendListener());
        this.AddFriendListenerThread = new Thread(addFriendServer);
    }

    public class AddFriendReq implements Runnable {
        private IUser user;

        AddFriendReq(IUser user) {
            this.user = user;
        }

        public String genAddFriendReqPacket(UUID targetUID){
            return "P2PProtocol:AUTH:REQ:" +  host.getID().toString() + ":" + targetUID.toString() + ":" + "P2PProtocol";
        }

        @Override
        public void run() {
            try {
                addFriendProcedureInitialized.acquire();
                SocketClient socketClient = new SocketClient(Network.IPAddress(serviceContext),serviceCfg.getAuthPort(), serviceCfg.getAuthTimeOut());
                socketClient.SendData(genAddFriendReqPacket(user.getID()));
                String packet = socketClient.ReceiveData(2048);
                if(packet==null)
                    return;
                String[] data = packet.split(":");
                if(data.length!=6)
                    return;
                if(data[0].equals("P2PProtocol") &&
                        data[1].equals("AUTH") &&
                        data[2].equals("CONFIRM") &&
                        UUID.fromString(data[3]).equals(user.getID()) &&
                        UUID.fromString(data[4]).equals(host.getID()) &&
                        data[5].equals("P2PProtocol")){
                    EventBus.getDefault().post(
                            new EAddFriendConfirmed(
                                    new OFriend(
                                            user.getName(),
                                            user.getID(),
                                            user.getNetworkAddress()
                                    )
                            )
                    );
                } else if(data[0].equals("P2PProtocol") &&
                        data[1].equals("AUTH") &&
                        data[2].equals("REJECT") &&
                        UUID.fromString(data[3]).equals(user.getID()) &&
                        UUID.fromString(data[4]).equals(host.getID()) &&
                        data[5].equals("P2PProtocol")){
                    EventBus.getDefault().post(new EAddFriendRejected(user));
                    socketClient.close();
                    return;
                }
                EventBus.getDefault().post(new EAddFriendRejected(user));
                socketClient.close();
            } catch (InterruptedException e) {
                EventBus.getDefault().post(new EAddFriendRejected(user));
                e.printStackTrace();
            }
            finally {
                addFriendProcedureInitialized.release();
            }
        }
    }

    public void NewAddFriendReq(IUser target){
        try {
            addFriendProcedureInitialized.acquire();
            Thread addFriendThread = new Thread(new AddFriendReq(target));
            addFriendThread.start();
            addFriendProcedureInitialized.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Stop(){
        AddFriendListenerThread.stop();
    }
}
