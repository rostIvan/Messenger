package trickyquestion.messenger.p2p_protocol.events;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 03.12.2017.
 */

public class EAddFriendRejected {
    private IUser friend;

    public EAddFriendRejected(IUser friend){
        this.friend = friend;
    }

    public IUser getFriend() {
        return friend;
    }
}
