package trickyquestion.messenger.Util.Protocol;

import java.util.UUID;

/**
 * Created by Zen on 13.09.2017.
 */
public class BuildProtocolMsg {
    /**
     * Create heartbeat packet data
     * @param UserName also know as user Login
     * @param UserID user id which identifies user in network
     * @param IP client smart phone/tablet IP address
     * @return return string value which represent protocol heartbeat packet
     */
    static public String genHeartbeatPacket(String UserName, UUID UserID, String IP){
        StringBuilder UsrNamePart = new StringBuilder("$$$$$$$$$$$$$$$$$$$");
        UsrNamePart.replace(0,UserName.length(),UserName);
        StringBuilder UserIDPart = new StringBuilder(UserID.toString());
        StringBuilder UserIPPart = new StringBuilder("$$$$$$$$$$$$$$$");
        UserIPPart.replace(0,IP.length(),IP);
        return new StringBuilder
                ("P2P_HEARTBEAT:" + UsrNamePart + ":" + UserIDPart + ":" +UserIPPart+ ":P2P_HEARTBEAT")
                .toString();
    }
}
