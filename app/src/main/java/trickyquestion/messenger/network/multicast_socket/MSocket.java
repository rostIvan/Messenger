package trickyquestion.messenger.network.multicast_socket;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import javax.annotation.CheckForNull;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;

/**
 * Created by Natsuki Subaru on 27.10.2017.
 */

public class MSocket {
    private static String logTag = "MSocket";

    private MSocket(){
        throw new IllegalStateException("Utility class");
    }

    public static void sendMsg(String ip, String msg, String groupIP, int port){
        try(MulticastSocket socket = new MulticastSocket(port)) {
            socket.setInterface(InetAddress.getByName(ip));
            socket.joinGroup(InetAddress.getByName(groupIP));
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            Log.d(logTag, "Send packet: " + msg);
            socket.send(new DatagramPacket(msg.getBytes(),msg.getBytes().length, InetAddress.getByName(groupIP),port));
            socket.leaveGroup(InetAddress.getByName(groupIP));
        } catch (IOException e) {
            Log.d(logTag, e.getMessage());
        }
    }

    @CheckForNull
    public static String receive(String ip, String groupIP, int port){
        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.setInterface(InetAddress.getByName(ip));
            socket.joinGroup(InetAddress.getByName(groupIP));
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            byte[] content = new byte[256];
            DatagramPacket packet = new DatagramPacket(content, content.length);
            socket.setSoTimeout(1000);
            if (Network.getCurrentNetworkState() == NetworkState.ACTIVE)
                socket.receive(packet);
            String data = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength()
            );
            Log.d(logTag, "Receive packet: " + data);
            socket.leaveGroup(InetAddress.getByName(groupIP));
            return data;
        } catch (SocketTimeoutException ignored){
            Log.d(logTag, "Receiving packet timeout");
        } catch (IOException e) {
            Log.d(logTag, e.getMessage());
        }
        return null;
    }
}
