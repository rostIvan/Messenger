package trickyquestion.messenger.Protocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import trickyquestion.messenger.Protocol.Interfaces.IClient;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Zen on 23.09.2017.
 */

public class P2PHeartbeat {
    public enum HeartbeatStatus{
        Stops, Configured, Run, InvalidSocket, Interupted
    }

    private IClient server;
    private Context context;

    volatile private boolean shutdown = true;
    volatile private Lock reconfiguring_lock = new ReentrantLock();
    volatile private Condition reconfiguring_end = reconfiguring_lock.newCondition();
    volatile private boolean reconfiguring_start = false;

    volatile MulticastSocket socket;

    private HeartbeatStatus status = HeartbeatStatus.Stops;

    private final Thread sender = new Thread(new Sender());

    P2PHeartbeat(MulticastSocket socket){
        this.socket = socket;
    }

    /**
     * Call to change configuration, for example call this on change user login
     * @param user user *data
     * @param context application context for getting ip
     */
    public void ReConfigure( IClient user, Context context)  {
        if (status == HeartbeatStatus.Run) {
            reconfiguring_start = true;
        }
        this.server = user;
        this.context = context;
        this.status = HeartbeatStatus.Configured;
        if(reconfiguring_start){
            reconfiguring_end.notifyAll();
            reconfiguring_start = false;
        }
    }

    HeartbeatStatus getStatus(){
        return status;
    }

    void Stop(){
        shutdown = true;
    }

    @SuppressLint("DefaultLocale")
    String getCurIP(){
        WifiManager wifiManager = (WifiManager)
                context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return String.format(
                "%d.%d.%d.%d",
                (ip & 0xff),
                (ip >> 8 & 0xff),
                (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));
    }

    DatagramPacket CreatePacket() throws UnknownHostException {
        String heartbeat_msg =
                BuildProtocolMsg.genHeartbeatPacket
                        (server.getName(),server.getID(),getCurIP());
        return new
                DatagramPacket( heartbeat_msg.getBytes(),
                                heartbeat_msg.length(),
                                InetAddress.getByName(P2PSpecification.GROUP_IP),
                                P2PSpecification.MULTICAST_PORT
        );
    }

    public void Broadcast(){
        sender.start();
    }
    class Sender implements Runnable{
        @Override
        public void run() {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            status = HeartbeatStatus.Run;
            shutdown = false;
            synchronized(reconfiguring_lock) {
                while (!shutdown) {
                    try {
                        if(reconfiguring_start) reconfiguring_end.wait();
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                            socket.send(CreatePacket());
                        Thread.sleep(2500);
                    } catch (IOException e) {
                        status = HeartbeatStatus.InvalidSocket;
                        break;
                    } catch (InterruptedException e) {
                        status = HeartbeatStatus.Interupted;
                        break;
                    }
                }
            }
            status=HeartbeatStatus.Stops;
        }
    }
}

