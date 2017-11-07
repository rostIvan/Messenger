package trickyquestion.messenger.util.event_bus_pojo;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 02.11.2017.
 */

public class ChangeUserList {
    private final List<IUser> users;
    public ChangeUserList(List<IUser> users){
        this.users = users;
    }

    public List<IUser> getNewList(){
        return users;
    }
}
