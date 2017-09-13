package trickyquestion.messenger.Util.Protocol;

import java.util.UUID;

import trickyquestion.messenger.Util.Constants;

/**
 * Created by Zen on 13.09.2017.
 */
public class BuildProtocolMsg {
    static public String BuildHeartbeat(String UserName, UUID UserID, String IP){
        StringBuilder FixedSizeUserName(Constants.USERNAME_MAX_LEN);
        FixedSizeUserName.replace(0,UserName.length(),UserName);
        //TODO: Write return and test algorithm
        return "";
    }
}
