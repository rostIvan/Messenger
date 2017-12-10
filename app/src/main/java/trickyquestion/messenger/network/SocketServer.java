package trickyquestion.messenger.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subaru on 11.12.2017.
 */

public class SocketServer implements Runnable{
    List<ISocketListener> listeners = new ArrayList<>();
    ServerSocket serverSocket;
    public SocketServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                new Thread(new SocketProccedRunnable(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ISocketListener{
        void proceed(String str, Socket socket);
    }

    public void registerListener(ISocketListener listener){
        listeners.add(listener);
    }

    private class SocketProccedRunnable implements Runnable{
        Socket socket;

        SocketProccedRunnable(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buf = new byte[1024];
                int sz = inputStream.read(buf);
                String data = new String(buf, 0, sz);
                for(ISocketListener listener : listeners)
                    if(listener!=null) listener.proceed(data, socket);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
