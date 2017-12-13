package trickyquestion.messenger.network.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Subaru on 11.12.2017.
 */

public class SocketClient {
    Socket socket;

    public SocketClient(String ip, int port, int timeout) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendData(String msg) {
        try {
            socket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ReceiveData(int maxPacketSize){
        try {
            byte[] buf = new byte[maxPacketSize];
            int size = socket.getInputStream().read(buf);
            return new String(buf, 0 ,size);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}