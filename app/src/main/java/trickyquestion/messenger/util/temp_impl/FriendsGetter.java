package trickyquestion.messenger.util.temp_impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.add_friend_screen.model.Friend;
import trickyquestion.messenger.add_friend_screen.model.IFriend;
import trickyquestion.messenger.p2p_protocol.ProtocolClientSide;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class FriendsGetter {
    public static List<IFriend> getFriends(int size) {
        final List<IFriend> ret = new ArrayList<>();
        final List<IUser> list = ProtocolClientSide.ProtocolInterface().getUsers();
        for (IUser user : list) {
            final IFriend friend = new Friend(user.getName(), user.getID(), null, true);
            ret.add(friend);
        }
        return ret;
    }
}
