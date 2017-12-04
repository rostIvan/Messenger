package trickyquestion.messenger.p2p_protocol.events;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 03.12.2017.
 */

public class authRejected {
    private IUser friend;

    public authRejected(IUser friend){
        this.friend = friend;
    }

    public IUser getFriend() {
        return friend;
    }
}
