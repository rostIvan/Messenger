package trickyquestion.messenger.Protocol.Implementation;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.locks.Lock;

import trickyquestion.messenger.Protocol.Interface.IAuthenticatedUser;
import trickyquestion.messenger.Util.Protocol.BuildProtocolMsg;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Zen on 12.09.2017.
 */

public class P2PHeartbeat implements Runnable {
    public enum HeartbeatStatus{
        Stops, Configured, Run, InvalidSocket, Interupted
    }

    private DatagramSocket socket;
    private IAuthenticatedUser server;
    private Context context;

    volatile private boolean shutdown = true;
    volatile private Lock reconfiguring;

    private HeartbeatStatus status = HeartbeatStatus.Stops;

    /**
     * Create Heartbeat packet sender
     * @param socket socket object which use to send data
     * @param user user data
     * @param context application context for getting ip
     */
    P2PHeartbeat(DatagramSocket socket, IAuthenticatedUser user, Context context){
        reconfiguring.unlock();
        ReConfigure(socket,user,context);
    }

    /**
     * Call to change configuration, for example call this on change user login
     * @param socket socket object which use to send data
     * @param user user data
     * @param context application context for getting ip
     */
    public void ReConfigure(DatagramSocket socket, IAuthenticatedUser user, Context context){
        boolean isReconfiguring = false;
        if (status == HeartbeatStatus.Run) {
            reconfiguring.lock();
        }
        this.socket = socket;
        this.server = user;
        this.context = context;
        this.status = HeartbeatStatus.Configured;
        if(isReconfiguring){
            reconfiguring.unlock();
        }
    }

    HeartbeatStatus getStatus(){
        return status;
    }

    @Override
    public void run() {
        //TODO: Replace getting ip method
        WifiManager wifiManager = (WifiManager)
                context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipString = String.format(
                "%d.%d.%d.%d",
                (ip & 0xff),
                (ip >> 8 & 0xff),
                (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));
        String heartbeat_msg =
                BuildProtocolMsg.genHeartbeatPacket
                        (server.getName(),server.getId(),ipString);
        DatagramPacket heartbeat_packet = new
                DatagramPacket(heartbeat_msg.getBytes(),heartbeat_msg.length());
        status = HeartbeatStatus.Run;
        shutdown = false;
        while(!shutdown){
            try {
                reconfiguring.wait();
                socket.send(heartbeat_packet);
                Thread.sleep(2500);
            }
            catch (IOException e){
                status = HeartbeatStatus.InvalidSocket;
                break;
            }
            catch (InterruptedException e){
                status = HeartbeatStatus.Interupted;
                break;
            }
        }
        status=HeartbeatStatus.Stops;
    }
}
