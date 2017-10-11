package trickyquestion.messenger.Protocol;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Zen on 23.09.2017.
 */

public class BuildProtocolMsg {
    /**
     * Create heartbeat packet data
     * @param UserName also know as user Login
     * @param UserID user id which identifies user in network
     * @param IP client smart phone/tablet IP address
     * @return return string value which represent protocol heartbeat packet(size 109)
     */
    static public String genHeartbeatPacket(String UserName, UUID UserID, String IP){
        StringBuilder UsrNamePart = new StringBuilder("$$$$$$$$$$$$$$$$$$$");
        UsrNamePart.replace(0,UserName.length(),UserName);
        StringBuilder UserIDPart = new StringBuilder(UserID.toString());
        StringBuilder UserIPPart = new StringBuilder("$$$$$$$$$$$$$$$");
	    Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss", Locale.US);
	    String SendTime = sdf.format(Calendar.getInstance().getTime());
        UserIPPart.replace(0,IP.length(),IP);
        return new String
                ("P2P_HEARTBEAT:" + UsrNamePart + ":" + UserIDPart + ":" +UserIPPart+ ":" + SendTime + ":P2P_HEARTBEAT");
    }
}