package trickyquestion.messenger.network;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Subaru on 11.12.2017.
 */

public class SocketClient {
    Socket socket;

    public SocketClient(String ip, int port) {
        try {
            socket = new Socket(ip,port);
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

    public String ReceiveData(int timeout, int maxPacketSize){
        try {
            socket.setSoTimeout(timeout);
            byte[] buf = new byte[maxPacketSize];
            int size = socket.getInputStream().read(buf);
            return new String(buf,size);
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
