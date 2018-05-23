package trickyquestion.messenger.p2p_protocol.modules;

import android.content.Context;
import android.util.Log;

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
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.preference.NetworkPreference;

/**
 * Created by Zen on 28.11.2017.
 */

public class P2PAddFriends {
    private NetworkPreference serviceCfg;
    private Context serviceContext;
    private P2PNetwork network;
    private IHost host;

    private Thread addFriendListenerThread;

    private volatile Semaphore addFriendProcedureInitialized = new Semaphore(1);

    private final String logTag = "P2PAddFriends";

    class AddFriendListener implements SocketServer.ISocketListener {
        volatile Semaphore addFriendMutex = new Semaphore(1);
        volatile Boolean authConfirmed = false;

        private String friendName;

        public void onEvent(EAddFriendConfirmed event) {
            if (event.getFriend().getName().equals(friendName)) {
                authConfirmed = true;
                addFriendMutex.release();
            }
        }

        public void onEvent(EAddFriendRejected event) {
            if (event.getFriend().getName().equals(friendName)) {
                authConfirmed = false;
                addFriendMutex.release();
            }
        }

        @NotNull
        String genConfirmPacket(@NotNull UUID target) {
            return "P2PProtocol:AUTH:CONFIRM:" + host.getID() + ":" + target.toString() + ":" + Constants.PROTOCOL_HEADER;
        }

        @NotNull
        String genRejectPacket(@NotNull UUID target) {
            return "P2PProtocol:AUTH:REJECT:" + host.getID() + ":" + target.toString() + ":" + Constants.PROTOCOL_HEADER;
        }

        IUser findUser(UUID targetID) {
            List<IUser> users = network.getUsers();
            for (IUser user : users) {
                if (user.getId().equals(targetID)) {
                    return user;
                }
            }
            return null;
        }

        private boolean isCorrectAuthReqPacket(String[] parts) {
            return parts.length == 6 &&
                    parts[0].equals(Constants.PROTOCOL_HEADER) &&
                    parts[1].equals("AUTH") &&
                    parts[2].equals("REQ") &&
                    parts[4].equals(host.getID().toString());
        }

        private void confirmFriendAdd(IUser targetUser){
            EventBus.getDefault().post(
                    new EAddFriendConfirmed(
                            new OFriend(
                                    targetUser.getName(),
                                    targetUser.getId(),
                                    targetUser.getNetworkAddress())
                    )
            );
        }

        @Override
        public void proceed(String str, Socket socket) {
            String[] parts = str.split(":");
            if (isCorrectAuthReqPacket(parts)) {
                IUser targetUser = findUser(UUID.fromString(parts[2]));
                if (targetUser == null) return;
                EventBus.getDefault().post(new EAddFriendRequest(targetUser));
                if(!addFriendMutex.tryAcquire((int) (serviceCfg.getAuthTimeOut() * 0.9))) return;
                else friendName = targetUser.getName();
                addFriendMutex.release();
                try {
                    if (authConfirmed) {
                        confirmFriendAdd(targetUser);
                        socket.getOutputStream().write(genConfirmPacket(UUID.fromString(parts[4])).getBytes());
                    } else
                        socket.getOutputStream().write(genRejectPacket(UUID.fromString(parts[4])).getBytes());
                } catch (IOException e) {
                    Log.d(logTag, e.getMessage());
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
        this.addFriendListenerThread = new Thread(addFriendServer);
    }

    public class AddFriendReq implements Runnable {
        private IUser user;

        AddFriendReq(IUser user) {
            this.user = user;
        }

        public String genAddFriendReqPacket(UUID targetUID){
            return "P2PProtocol:AUTH:REQ:" +  host.getID().toString() + ":" + targetUID.toString() + ":" + Constants.PROTOCOL_HEADER;
        }

        @Override
        public void run() {
            try {
                addFriendProcedureInitialized.acquire();
                SocketClient socketClient = new SocketClient(Network.ipAddress(serviceContext),serviceCfg.getAuthPort(), serviceCfg.getAuthTimeOut());
                socketClient.sendData(genAddFriendReqPacket(user.getId()));
                String packet = socketClient.receiveData(2048);
                if(packet==null)
                    return;
                String[] data = packet.split(":");
                if(data.length!=6)
                    return;
                if(data[0].equals(Constants.PROTOCOL_HEADER) &&
                        data[1].equals("AUTH") &&
                        data[2].equals("CONFIRM") &&
                        UUID.fromString(data[3]).equals(user.getId()) &&
                        UUID.fromString(data[4]).equals(host.getID()) &&
                        data[5].equals(Constants.PROTOCOL_HEADER)){
                    EventBus.getDefault().post(
                            new EAddFriendConfirmed(
                                    new OFriend(
                                            user.getName(),
                                            user.getId(),
                                            user.getNetworkAddress()
                                    )
                            )
                    );
                } else if(data[0].equals(Constants.PROTOCOL_HEADER) &&
                        data[1].equals("AUTH") &&
                        data[2].equals("REJECT") &&
                        UUID.fromString(data[3]).equals(user.getId()) &&
                        UUID.fromString(data[4]).equals(host.getID()) &&
                        data[5].equals(Constants.PROTOCOL_HEADER)){
                    EventBus.getDefault().post(new EAddFriendRejected(user));
                    socketClient.close();
                    return;
                }
                EventBus.getDefault().post(new EAddFriendRejected(user));
                socketClient.close();
            } catch (InterruptedException e) {
                EventBus.getDefault().post(new EAddFriendRejected(user));
                Log.d(logTag, e.getMessage());
                Thread.currentThread().interrupt();
            }
            finally {
                addFriendProcedureInitialized.release();
            }
        }
    }

    public void newAddFriendReq(IUser target){
        try {
            addFriendProcedureInitialized.acquire();
            Thread addFriendThread = new Thread(new AddFriendReq(target));
            addFriendThread.start();
            addFriendProcedureInitialized.release();
        } catch (InterruptedException e) {
            Log.d("P2PAddFriends", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void stop(){
        addFriendListenerThread.interrupt();
    }
}
