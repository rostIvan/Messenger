package trickyquestion.messenger.util.temp_impl;


import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.add_friend_screen.model.User;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class FriendsGetter {
    public static List<IUser> getFriends() {
        final List<IUser> ret = new ArrayList<>();
        final List<IUser> list = P2PProtocolConnector.ProtocolInterface().getUsers();
        for (IUser user : list) {
            final User friend = new User();
            friend.setName(user.getName());
            friend.setId(user.getID());
            friend.setNetworkAddress(user.getNetworkAddress());
            ret.add(friend);
        }
        return ret;
    }
}
