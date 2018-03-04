package trickyquestion.messenger.util.android.event_bus_pojo;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class ChangeUserList {
    private final IUser user;
    private final boolean exit;

    public ChangeUserList(IUser user, boolean exist) {
        this.user = user;
        this.exit = exist;
    }

    public IUser getUser() {
        return user;
    }

    public boolean exist(){
        return exit;
    }
}
