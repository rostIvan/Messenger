package trickyquestion.messenger.p2p_protocol;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
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
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.network.MSocket;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.NetworkStateChanged;
import trickyquestion.messenger.network.SocketClient;
import trickyquestion.messenger.network.SocketServer;
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
    private SocketServer serverSocket;

    private Thread MessagesListener;

    public P2PMesseges(NetworkPreference serviceCfg, IHost host, Context context){
        this.serviceCfg = serviceCfg;
        this.host = host;
        this.context = context;
        this.serverSocket = new SocketServer(serviceCfg.getMsgPort());
        this.serverSocket.registerListener(new MessagesReceiverListener());
        this.MessagesListener = new Thread(serverSocket);
        this.MessagesListener.start();
    }

    public void Stop(){
        serverSocket.close();
    }

    private class MessagesReceiverListener implements SocketServer.ISocketListener{
        @Override
        public void proceed(String str, Socket socket) {
            String[] packetContent = str.split(":");
            if(packetContent.length != 6) return;
            if(!packetContent[0].equals("P2PProtocol") || !packetContent[1].equals("MSG")) return;
            List<IFriend> friends = TypeCasting.castToIFriendList(FriendsRepository.getFriends());
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
                if (from == null) return;
                aesKey = new SecretKeySpec(from.encKey(), 0, 16, "AES");
            }
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, aesKey);
                byte[] uncryptedMsg = cipher.doFinal(HexConv.hexToByte(FixedString.toDynamicSize(packetContent[3], '$')));
                String msg = new String(uncryptedMsg, "UTF-8");
                EventBus.getDefault().post(new ReceivedMsg(msg,from));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
    }

    class MsgSenderRunner implements Runnable{

        private IFriend target;
        private String msg;

        MsgSenderRunner(IFriend target, String msg){
            this.target = target;
            this.msg = msg;
        }

        public void SendMsg(IFriend target, String msg){
            if(target.getID().equals(host.getID())) {SendSelfMsg(msg); return;}
            try {
                SecretKeySpec aesKey = new SecretKeySpec(target.encKey(), 0, 16, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                byte[] data = cipher.doFinal(msg.getBytes());
                String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
                String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + target.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
                SocketClient socketClient = new SocketClient(target.getNetworkAddress(),serviceCfg.getMsgPort());
                socketClient.SendData(packet);
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
                SocketClient socketClient = new SocketClient(target.getNetworkAddress(), serviceCfg.getMsgPort());
                socketClient.SendData(packet);
                socketClient.close();
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
            }
        }

        @Override
        public void run() {
            SendMsg(target, msg);
        }
    }

    private Thread MsgSender;

    void SendMsg(IFriend target, String msg){
        MsgSender = new Thread(new MsgSenderRunner(target, msg));
        MsgSender.start();
    }
}
