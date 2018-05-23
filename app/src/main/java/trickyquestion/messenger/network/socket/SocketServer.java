package trickyquestion.messenger.network.socket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subaru on 11.12.2017.
 */

public class SocketServer implements Runnable {
    List<ISocketListener> listeners = new ArrayList<>();
    ServerSocket serverSocket;

    private static String TAG_LOG = "SocketServer";

    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            Log.d(TAG_LOG, e.getMessage());
        }
    }

    @Override
    public void run() {
        while (Thread.interrupted()) {
            try {
                new Thread(new SocketProccedRunnable(serverSocket.accept())).start();
            } catch (IOException e) {
                Log.d(TAG_LOG, e.getMessage());
            }
        }
    }

    public interface ISocketListener {
        void proceed(String str, Socket socket);
    }

    public void registerListener(ISocketListener listener) {
        listeners.add(listener);
    }

    private class SocketProccedRunnable implements Runnable {
        Socket socket;

        SocketProccedRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buf = new byte[2048];
                int sz = inputStream.read(buf);
                String data = new String(buf, 0, sz);
                for (ISocketListener listener : listeners)
                    if (listener != null) listener.proceed(data, socket);
                socket.close();
            } catch (IOException e) {
                Log.d(TAG_LOG, e.getMessage());
            }
        }
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.d(TAG_LOG, e.getMessage());
        }
    }
}
