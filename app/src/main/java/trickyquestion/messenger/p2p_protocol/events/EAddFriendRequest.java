package trickyquestion.messenger.p2p_protocol.events;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 04.12.2017.
 */

public class EAddFriendRequest {
    private IUser from;
    public EAddFriendRequest(IUser from){
        this.from = from;
    }

    public IUser getFrom() {
        return from;
    }
}
