package trickyquestion.messenger.p2p_protocol.modules;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.screen.main.tabs.friends.repository.FriendsRepository;
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
            if(host.getID().equals(sender)){
                for(IFriend friend :friends){
                    if(host.getID().equals(friend.getID())){
                        from = friend;
                        break;
                    }
                }
            }else {
                for (IFriend friend : friends) {
                    if (friend.getID().equals(sender)) {
                        from = friend;
                        break;
                    }
                }
                if (from == null) return;
            }
            try {
                byte[] uncryptedMsg = HexConv.hexToByte(FixedString.toDynamicSize(packetContent[4],'$'));
                String msg = new String(uncryptedMsg, "UTF-8");
                EventBus.getDefault().post(new EReceivedMsg(msg,from));
            } catch (UnsupportedEncodingException e) {
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

        void SendMsg(IFriend target, String msg){
            if(target.getID().equals(host.getID())) {SendSelfMsg(msg); return;}
            byte[] data = msg.getBytes();
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + target.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            SocketClient socketClient = new SocketClient(target.getNetworkAddress(),serviceCfg.getMsgPort(), 0);
            socketClient.SendData(packet);
            socketClient.close();
        }

        void SendSelfMsg(String msg){
            byte[] data = msg.getBytes();
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + host.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            SocketClient socketClient = new SocketClient(target.getNetworkAddress(), serviceCfg.getMsgPort(), 0);
            socketClient.SendData(packet);
            socketClient.close();
        }

        @Override
        public void run() {
            SendMsg(target, msg);
        }
    }

    private Thread MsgSender;

    public void SendMsg(IFriend target, String msg){
        if(MsgSender!=null)
            try {
                MsgSender.join();
            } catch (InterruptedException e) {}
        MsgSender = new Thread(new MsgSenderRunner(target, msg));
        MsgSender.start();
    }
}
