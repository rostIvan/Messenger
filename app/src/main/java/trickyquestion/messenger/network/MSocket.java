package trickyquestion.messenger.network;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Zen on 27.10.2017.
 */

public class MSocket {
    static public void SendMsg(String msg,String groupIP, int port){
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(InetAddress.getByName(groupIP));
            socket.send(new DatagramPacket(msg.getBytes(),msg.getBytes().length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    static public String Receive(String groupIP, int port){
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(InetAddress.getByName(groupIP));
            byte[] content = new byte[1000];
            DatagramPacket packet = new DatagramPacket(content,content.length);
            return new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
