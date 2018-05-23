package trickyquestion.messenger.p2p_protocol.modules;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.UUID;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.network.socket.SocketClient;
import trickyquestion.messenger.network.socket.SocketServer;
import trickyquestion.messenger.p2p_protocol.events.EReceivedMsg;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.preference.NetworkPreference;
import trickyquestion.messenger.util.java.string_helper.FixedString;
import trickyquestion.messenger.util.java.string_helper.HexConv;
import trickyquestion.messenger.util.java.maping.TypeCasting;

/**
 * Created by Zen on 04.12.2017.
 */

public class P2PMesseges {
    private NetworkPreference serviceCfg;
    private IHost host;
    private SocketServer serverSocket;

    private Thread messagesListener;

    public P2PMesseges(NetworkPreference serviceCfg, IHost host){
        this.serviceCfg = serviceCfg;
        this.host = host;
        this.serverSocket = new SocketServer(serviceCfg.getMsgPort());
        this.serverSocket.registerListener(new MessagesReceiverListener());
        this.messagesListener = new Thread(serverSocket);
        this.messagesListener.start();
    }

    public void stop(){
        serverSocket.close();
    }

    private class MessagesReceiverListener implements SocketServer.ISocketListener{
        private boolean verifyMsgPacket(String[] packetContent){
            return packetContent.length != 6 &&
                    packetContent[0].equals("P2PProtocol") &&
                    packetContent[1].equals("MSG");
        }

        @Override
        public void proceed(String str, Socket socket) {
            String[] packetContent = str.split(":");
            if(!verifyMsgPacket(packetContent)) return;
            UUID sender = UUID.fromString(packetContent[2]);
            IFriend from  = TypeCasting.castToIFriend(FriendRepository.INSTANCE.findById(sender));
            try {
                byte[] unencryptedMsg = HexConv.hexToByte(FixedString.toDynamicSize(packetContent[4],'$'));
                String msg = new String(unencryptedMsg, "UTF-8");
                EventBus.getDefault().post(new EReceivedMsg(msg,from));
            } catch (UnsupportedEncodingException e) {
                Log.d("P2PMesseges", e.getMessage());
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

        void sendMsg(IFriend target, String msg){
            if(target.getID().equals(host.getID())) {
                sendSelfMsg(msg); return;}
            byte[] data = msg.getBytes();
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + target.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            SocketClient socketClient = new SocketClient(target.getNetworkAddress(),serviceCfg.getMsgPort(), 0);
            socketClient.sendData(packet);
            socketClient.close();
        }

        void sendSelfMsg(String msg){
            byte[] data = msg.getBytes();
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + host.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            SocketClient socketClient = new SocketClient(target.getNetworkAddress(), serviceCfg.getMsgPort(), 0);
            socketClient.sendData(packet);
            socketClient.close();
        }

        @Override
        public void run() {
            sendMsg(target, msg);
        }
    }

    private Thread msgSender;

    public void sendMsg(IFriend target, String msg){
        if(msgSender !=null)
            try {
                msgSender.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        msgSender = new Thread(new MsgSenderRunner(target, msg));
        msgSender.start();
    }
}
