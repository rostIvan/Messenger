package trickyquestion.messenger.Protocol.Implementation;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import trickyquestion.messenger.Protocol.Interface.IAuthenticatedUser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Zen on 12.09.2017.
 */

public class P2PHeartbeat implements Runnable {
    private DatagramSocket socket;

    P2PHeartbeat(DatagramSocket socket, IAuthenticatedUser user){
        this.socket = socket;
    }

    @Override
    public void run() {
        //TODO:Write
    }
}
