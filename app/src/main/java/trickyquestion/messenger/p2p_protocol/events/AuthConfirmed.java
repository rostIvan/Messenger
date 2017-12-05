package trickyquestion.messenger.p2p_protocol.events;


import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;

/**
 * Created by Zen on 03.12.2017.
 */

public class AuthConfirmed {
    private IFriend friend;

    public AuthConfirmed(IFriend friend){
        this.friend = friend;
    }

    public IFriend getFriend() {
        return friend;
    }
}
