package trickyquestion.messenger.network.socket;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.annotation.CheckForNull;

/**
 * Created by Natsuki Subaru on 11.12.2017.
 */

public class SocketClient {
    private Socket socket;
    private String logTag = "SocketClient";

    public SocketClient(String ip, int port, int timeout) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),timeout);
        } catch (IOException e) {
            Log.d(logTag, e.getMessage());
        }
    }

    public void sendData(String msg) {
        try {
            socket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            Log.d(logTag, e.getMessage());
        }
    }

    @CheckForNull
    public String receiveData(int maxPacketSize){
        try {
            byte[] buf = new byte[maxPacketSize];
            int size = socket.getInputStream().read(buf);
            return new String(buf, 0, size);
        } catch (IOException e) {
            Log.d(logTag, e.getMessage());
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