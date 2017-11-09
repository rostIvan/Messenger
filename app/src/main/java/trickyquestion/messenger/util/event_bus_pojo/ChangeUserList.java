package trickyquestion.messenger.util.event_bus_pojo;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class ChangeUserList {
    private final IUser user;
    private final boolean isExist;

    public ChangeUserList(IUser user, boolean isExist) {
        this.user = user;
        this.isExist = isExist;
    }

    public IUser getUser() {
        return user;
    }

    public boolean isExist(){
        return isExist;
    }
}
