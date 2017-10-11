package trickyquestion.messenger.p2p_protocol;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import trickyquestion.messenger.p2p_protocol.interfaces.IClient;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Zen on 11.10.2017.
 */

public class heartbeat {
    /**
     * Create heartbeat packet data
     * @param UserName also know as user Login
     * @param UserID user id which identifies user in network
     * @param IP client smart phone/tablet IP address
     * @return return string value which represent protocol heartbeat packet(size 109)
     */
    private String genHeartbeatPacket(String UserName, UUID UserID, String IP){
        StringBuilder UsrNamePart = new StringBuilder("$$$$$$$$$$$$$$$$$$$");
        UsrNamePart.replace(0,UserName.length(),UserName);
        StringBuilder UserIDPart = new StringBuilder(UserID.toString());
        StringBuilder UserIPPart = new StringBuilder("$$$$$$$$$$$$$$$");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss", Locale.US);
        String SendTime = sdf.format(Calendar.getInstance().getTime());
        UserIPPart.replace(0,IP.length(),IP);
        return ("P2P_HEARTBEAT:" +
                UsrNamePart + ":" +
                UserIDPart + ":" +
                UserIPPart+ ":" +
                SendTime +
                ":P2P_HEARTBEAT");
    }
    public enum HeartbeatStatus{
        Stops, Configured, Run, InvalidSocket, Interupted
    }

    private IClient server;
    private Context context;

    //Looks and booleans for correct changing user data and shutdown service
    volatile private boolean shutdown = true;
    volatile private Lock reconfiguring_lock = new ReentrantLock();
    volatile private Condition reconfiguring_end = reconfiguring_lock.newCondition();
    volatile private boolean reconfiguring_start = false;

    //Socket for sending heartbeat packet
    volatile MulticastSocket socket;

    private HeartbeatStatus status = HeartbeatStatus.Stops;

    //Thread for sending heartbeat packet
    private final Thread sender = new Thread(new Sender());

    heartbeat(MulticastSocket socket){
        this.socket = socket;
    }

    /**
     * Call to change configuration, for example call this on change user login
     * @param user user *data
     * @param context application context for getting ip
     */
    public void ReConfigure(IClient user, Context context)  {
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

    /**
     * @return Heartbeat status
     */
    HeartbeatStatus getStatus(){
        return status;
    }

    /**
     * Stop sending heartbeat
     */
    void Stop(){
        shutdown = true;
    }

    /**
     * @return Current wifi ip
     */
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

    /**
     * Creating datagram packet which send as heartbeat
     * @return datagram packet which send as heartbeat
     * @throws UnknownHostException
     */
    DatagramPacket CreatePacket() throws UnknownHostException {
        String heartbeat_msg =
                genHeartbeatPacket
                        (server.getName(),server.getID(),getCurIP());
        return new
                DatagramPacket( heartbeat_msg.getBytes(),
                heartbeat_msg.length(),
                InetAddress.getByName(PROTOCOL_CFG.MULTICAST_GROUP_IP()),
                PROTOCOL_CFG.MULTICAST_PORT()
        );
    }

    /**
     * Start broadcasting
     */
    public void Broadcast(){
        sender.start();
    }

    /**
     *
     */
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
                        //Checking is reconfiguring started
                        if(reconfiguring_start) reconfiguring_end.wait();
                        //Checking is wifi active, if active send packet
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                            socket.send(CreatePacket());
                        //Sleep thread
                        Thread.sleep(PROTOCOL_CFG.HEARTBEAT_FREQUENCY());
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
