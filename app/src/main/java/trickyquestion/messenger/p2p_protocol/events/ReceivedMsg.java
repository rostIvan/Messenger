package trickyquestion.messenger.p2p_protocol.events;

import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;

/**
 * Created by Zen on 06.12.2017.
 */

public class ReceivedMsg {
    String msg;
    IFriend from;

    public ReceivedMsg(String msg, IFriend from){
        this.msg = msg;
        this.from = from;
    }

    public IFriend getFrom() {
        return from;
    }

    public String getMsg() {
        return msg;
    }
}
