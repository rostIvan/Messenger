package trickyquestion.messenger.network;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

/**
 * Created by Zen on 27.10.2017.
 */

public class MSocket {
    static public void SendMsg(String ip, String msg, String groupIP, int port){
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.setInterface(InetAddress.getByName(ip));
            socket.joinGroup(InetAddress.getByName(groupIP));
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            Log.d("MSocket", "Send packet: " + msg);
            socket.send(new DatagramPacket(msg.getBytes(),msg.getBytes().length, InetAddress.getByName(groupIP),port));
            socket.leaveGroup(InetAddress.getByName(groupIP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    static public String Receive(String ip, String groupIP, int port){
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.setInterface(InetAddress.getByName(ip));
            socket.joinGroup(InetAddress.getByName(groupIP));
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            byte[] content = new byte[256];
            DatagramPacket packet = new DatagramPacket(content,content.length);
            socket.setSoTimeout(1000);
            if(Network.GetCurrentNetworkState()==NetworkState.ACTIVE)
                socket.receive(packet);
            String data = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength()
            );
            Log.d("MSocket","Receive packet: " + data);
            socket.leaveGroup(InetAddress.getByName(groupIP));
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
