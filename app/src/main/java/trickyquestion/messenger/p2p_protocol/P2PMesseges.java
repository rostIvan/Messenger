package trickyquestion.messenger.p2p_protocol;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.network.MSocket;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.NetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.events.ReceivedMsg;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.preference.NetworkPreference;
import trickyquestion.messenger.util.string_helper.FixedString;
import trickyquestion.messenger.util.string_helper.HexConv;
import trickyquestion.messenger.util.type_cast.TypeCasting;

/**
 * Created by Zen on 04.12.2017.
 */

public class P2PMesseges {
    private NetworkPreference serviceCfg;
    private IHost host;
    private Context context;

    public P2PMesseges(NetworkPreference serviceCfg, IHost host, Context context){
        this.serviceCfg = serviceCfg;
        this.host = host;
        this.context = context;
        this.MessengesReceiver = new Thread(new MessengesReceiverRunnable());
        this.MessengesReceiver.start();
    }

    public void Stop(){
        this.MessengesReceiver.interrupt();
    }

    private class MessengesReceiverRunnable implements Runnable{
        volatile Semaphore networkAvailability = new Semaphore(1);

        private List<IFriend> getFriends(){
            return TypeCasting.castToIFriendList(FriendsRepository.getFriends());
        }

        MessengesReceiverRunnable(){
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

        @Override
        public void run() {
            for (;;) {
                if (networkAvailability.availablePermits() == 0) {
                    networkAvailability.tryAcquire();
                    networkAvailability.release();
                }
                if(Network.GetCurrentNetworkState()!=NetworkState.ACTIVE) continue;
                try {
                    DatagramSocket socket = new DatagramSocket(
                            serviceCfg.getMsgPort(),
                            InetAddress.getByName(Network.IPAddress(context)));
                    byte[] msgBytes = new byte[1500]; //MTU size
                    DatagramPacket packet = new DatagramPacket(msgBytes,1500);
                    socket.setSoTimeout(2048);
                    socket.receive(packet);
                    String[] packetContent = new String(
                            packet.getData(),
                            packet.getOffset(),
                            packet.getLength()
                    ).split(":");
                    if(packetContent.length != 6) continue;
                    if(!packetContent[0].equals("P2PProtocol") || !packetContent[1].equals("MSG")) continue;
                    List<IFriend> friends = getFriends();
                    IFriend from = null;
                    UUID sender = UUID.fromString(packetContent[2]);
                    SecretKeySpec aesKey;
                    if(host.getID().equals(sender)){
                        aesKey = new SecretKeySpec(host.getSelfEncKey(),0,16,"AES");
                    }else {
                        for (IFriend friend : friends) {
                            if (friend.getID().equals(sender)) {
                                from = friend;
                                break;
                            }
                        }
                        if (from == null) continue;
                        aesKey = new SecretKeySpec(from.encKey(), 0, 16, "AES");
                    }
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, aesKey);
                    byte[] uncryptedMsg = cipher.doFinal(HexConv.hexToByte(FixedString.toDynamicSize(packetContent[3], '$')));
                    String msg = new String(uncryptedMsg, "UTF-8");
                    EventBus.getDefault().post(new ReceivedMsg(msg,from));
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Thread MessengesReceiver;

    public void SendMsg(IFriend target, String msg){
        if(target.getID().equals(host.getID())) SendSelfMsg(msg);
        try {
            SecretKeySpec aesKey = new SecretKeySpec(target.encKey(), 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] data = cipher.doFinal(msg.getBytes());
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + target.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            byte[] buff = new byte[packet.length()];
            InetSocketAddress destAddress = new InetSocketAddress(
                    target.getNetworkAddress(),
                    serviceCfg.getMsgPort()
            );
            DatagramPacket datagramPacket = new DatagramPacket(buff, packet.length() + 69, destAddress);
            datagramPacket.setData(packet.getBytes());
            DatagramSocket socket = new DatagramSocket(
                    serviceCfg.getMsgPort(),
                    InetAddress.getByName(Network.IPAddress(context)));
            socket.send(datagramPacket);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendSelfMsg(String msg){
        try {
            SecretKeySpec aesKey = new SecretKeySpec(host.getSelfEncKey(), 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] data = cipher.doFinal(msg.getBytes());
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + host.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            byte[] buff = new byte[packet.length()];
            InetSocketAddress destAddress = new InetSocketAddress(
                    Network.IPAddress(context),
                    serviceCfg.getMsgPort()
            );
            DatagramPacket datagramPacket = new DatagramPacket(buff, packet.length() + 69, destAddress);
            datagramPacket.setData(packet.getBytes());
            DatagramSocket socket = new DatagramSocket(
                    serviceCfg.getMsgPort(),
                    InetAddress.getByName(Network.IPAddress(context)));
            socket.send(datagramPacket);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
