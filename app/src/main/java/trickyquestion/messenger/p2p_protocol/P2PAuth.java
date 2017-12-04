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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.NetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.events.authConfirmed;
import trickyquestion.messenger.p2p_protocol.events.authRejected;
import trickyquestion.messenger.p2p_protocol.events.authRequest;
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

    private volatile Semaphore authProcedureInitialized = new Semaphore(1);

    public P2PAuth(Context serviceContext, NetworkPreference serviceCfg, P2PNetwork network, IHost host){
        this.serviceCfg = serviceCfg;
        this.serviceContext = serviceContext;
        this.network = network;
        this.host = host;
        AuthListenerThread = new Thread(new AuthListener());
        AuthListenerThread.start();
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

        @Nullable
        private DatagramPacket genAuthPhase1(String dhPubKey, int port) {
            try {
                byte[] buff = new byte[dhPubKey.length() + 69];
                InetSocketAddress destAddress = new InetSocketAddress(user.getNetworkAddress(), port);
                DatagramPacket datagramPacket = null;
                datagramPacket = new DatagramPacket(buff, dhPubKey.length() + 69, destAddress);
                datagramPacket.setData(genAuthReqMsg(dhPubKey, host.getID()).getBytes());
                return datagramPacket;
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }

        private DatagramPacket phase2authPacket() {
            byte[] buf = new byte[serviceCfg.getAuthKeyBitSize() + 69];
            DatagramPacket datagramPacket = new DatagramPacket(buf, serviceCfg.getAuthKeyBitSize() + 69);
            return datagramPacket;
        }

        @Override
        public void run() {
            try {
                authProcedureInitialized.acquire();
                ServerSide serverSide = new ServerSide(serviceCfg.getAuthKeyBitSize());
                String key = serverSide.FirstPhase();
                DatagramSocket authSocket =
                        new DatagramSocket(
                                serviceCfg.getAuthPort(),
                                InetAddress.getByName(Network.IPAddress(serviceContext)));
                DatagramPacket authPhase1Packet =
                        genAuthPhase1(
                                FixedString.fill(key, '$', serviceCfg.getAuthKeyBitSize()),
                                serviceCfg.getAuthPort());
                authSocket.send(authPhase1Packet);
                DatagramPacket phase2authPacket = phase2authPacket();
                authSocket.setSoTimeout(serviceCfg.getAuthTimeOut());
                authSocket.receive(phase2authPacket);
                String packetContent = new String(
                        phase2authPacket.getData(),
                        phase2authPacket.getOffset(),
                        phase2authPacket.getLength()
                );
                String[] packetParts = packetContent.split(":");
                if (packetParts.length != 6) {
                    EventBus.getDefault().post(new authRejected(user));
                    return;
                }
                if (!(packetParts[0].equals("P2PProtocol") &&
                        packetParts[1].equals("AUTH") &&
                        packetParts[2].equals("REQ") &&
                        packetParts[3].equals(user.getID().toString()))) {
                    EventBus.getDefault().post(new authRejected(user));
                    return;
                }
                String clientPubKey = FixedString.toDynamicSize(packetParts[4], '$');
                serverSide.SecondPhase(clientPubKey);
                EventBus.getDefault().post(
                        new authConfirmed(
                                new P2PFriend(
                                        user.getName(),
                                        user.getID(),
                                        user.getNetworkAddress(),
                                        serverSide.getCipherKey())));
            } catch (IOException e) {
                EventBus.getDefault().post(new authRejected(user));
                e.printStackTrace();
            } catch (InterruptedException e) {
                EventBus.getDefault().post(new authRejected(user));
                e.printStackTrace();
            }
            finally {
                authProcedureInitialized.release();
            }
        }
    }

    private class AuthListener implements Runnable {
        volatile Semaphore networkAvailability = new Semaphore(1);
        volatile Semaphore authMutex = new Semaphore(1);

        volatile Boolean authConfirmed = false;

        AuthListener() {
            networkAvailability.release();
            EventBus.getDefault().register(this);
        }

        public void onEvent(NetworkStateChanged event) {
            if (event.getNewNetworkState() == NetworkState.INACTIVE) {
                try {
                    networkAvailability.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                networkAvailability.release();
            }
        }

        public void onEvent(authConfirmed e){
            authConfirmed = true;
            authMutex.release();
        }

        public void onEvent(authRejected e){
            authConfirmed = false;
            authMutex.release();
        }

        @NonNull
        @Contract(pure = true)
        private String genAuthConMsg(String dhPubKey, UUID my) {
            return "P2PProtocol:AUTH:CON:" + dhPubKey + ":" + my.toString() + ":P2Protocol";//21
        }

        @Nullable
        private DatagramPacket genAuthPhase2(String dhPubKey, int port, IUser target) {
            try {
                byte[] buff = new byte[dhPubKey.length() + 69];
                InetSocketAddress destAddress = new InetSocketAddress(target.getNetworkAddress(), port);
                DatagramPacket datagramPacket = null;
                datagramPacket = new DatagramPacket(buff, dhPubKey.length() + 69, destAddress);
                datagramPacket.setData(genAuthConMsg(dhPubKey, host.getID()).getBytes());
                return datagramPacket;
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }

        private DatagramPacket genAuthRejectPacket(int port, IUser target){
            try {
                byte[] buff = new byte[69];
                InetSocketAddress destAddress = new InetSocketAddress(target.getNetworkAddress(), port);
                DatagramPacket datagramPacket = null;
                String content = "P2PProtocol:AUTH:REJECT:"+host.getID().toString()+":AUTH:P2PProtocol";
                datagramPacket = new DatagramPacket(buff, content.length() + 69, destAddress);
                datagramPacket.setData(genAuthConMsg(content, host.getID()).getBytes());
                return datagramPacket;
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }

        private DatagramPacket phase1authPacket() {
            byte[] buf = new byte[serviceCfg.getAuthKeyBitSize() + 69];
            DatagramPacket datagramPacket = new DatagramPacket(buf, serviceCfg.getAuthKeyBitSize() + 69);
            return datagramPacket;
        }

        @Override
        public void run() {
            for (; ; ) {
                if (networkAvailability.availablePermits() == 0) {
                    networkAvailability.tryAcquire();
                    networkAvailability.release();
                }
                try {
                    authConfirmed = false;
                    authMutex.acquire();
                    DatagramSocket socket = new DatagramSocket(serviceCfg.getAuthPort(), InetAddress.getByName(Network.IPAddress(serviceContext)));
                    socket.setSoTimeout(serviceCfg.getAuthTimeOut());
                    DatagramPacket packet = phase1authPacket();
                    socket.receive(packet);
                    authProcedureInitialized.acquire();
                    ClientSide clientSide = new ClientSide();
                    String packetContent = new String(
                            packet.getData(),
                            packet.getOffset(),
                            packet.getLength()
                    );
                    String[] packetParts = packetContent.split(":");
                    if (packetParts.length != 6) {
                        continue;
                    }
                    if (!(packetParts[0].equals("P2PProtocol") &&
                            packetParts[1].equals("AUTH") &&
                            packetParts[2].equals("REQ"))) {
                        continue;
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
                        continue;
                    }

                    EventBus.getDefault().post(new authRequest(targetUser));
                    authMutex.tryAcquire((int)(serviceCfg.getAuthTimeOut()*0.9));
                    authMutex.release();
                    if(!authConfirmed) {
                        DatagramPacket reject = genAuthRejectPacket(serviceCfg.getAuthPort(),targetUser);
                        socket.send(reject);
                        continue;
                    }
                    String serverPubKey = FixedString.toDynamicSize(packetParts[4], '$');
                    String clientPubKey = FixedString.fill(
                            clientSide.FirstPhase(serverPubKey),
                            '$',
                            serviceCfg.getAuthKeyBitSize()
                    );
                    DatagramPacket authConPacket = genAuthPhase2(clientPubKey,serviceCfg.getAuthPort(),targetUser);
                    socket.send(authConPacket);
                    EventBus.getDefault().post(
                            new authConfirmed(
                                    new P2PFriend(
                                            targetUser.getName(),
                                            targetUser.getID(),
                                            targetUser.getNetworkAddress(),
                                            clientSide.getCipherKey())
                            )
                    );
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    authProcedureInitialized.release();
                }
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
