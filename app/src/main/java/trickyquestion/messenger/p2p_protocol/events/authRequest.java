package trickyquestion.messenger.p2p_protocol.events;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 04.12.2017.
 */

public class authRequest {
    private IUser from;
    public authRequest(IUser from){
        this.from = from;
    }

    public IUser getFrom() {
        return from;
    }
}
