package trickyquestion.messenger.util.temp_impl;


import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.add_friend_screen.model.User;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class FriendsGetter {
    public static List<IUser> getUsers() {
        final List<IUser> ret = new ArrayList<>();
        final List<IUser> list = P2PProtocolConnector.ProtocolInterface().getUsers();
        for (IUser user : list) {
            final User user1 = new User();
            user1.setName(user.getName());
            user1.setId(user.getID());
            user1.setNetworkAddress(user.getNetworkAddress());
            ret.add(user1);
        }
        return ret;
    }
}
