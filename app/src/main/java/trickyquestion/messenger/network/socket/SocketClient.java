package trickyquestion.messenger.network.socket;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Subaru on 11.12.2017.
 */

public class SocketClient {
    private Socket socket;

    public SocketClient(String ip, int port, int timeout) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),timeout);
        } catch (IOException e) {
            Log.d("SocketClient", e.getMessage());
        }
    }

    public void SendData(String msg) {
        try {
            socket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            Log.d("SocketClient", e.getMessage());
        }
    }

    public String ReceiveData(int maxPacketSize){
        try {
            byte[] buf = new byte[maxPacketSize];
            int size = socket.getInputStream().read(buf);
            return new String(buf, 0 ,size);
        } catch (SocketException e) {
            Log.d("SocketClient", e.getMessage());
        } catch (IOException e) {
            Log.d("SocketClient", e.getMessage());
        }
        return null;
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            Log.d("SocketClient", e.getMessage());
        }
    }
}