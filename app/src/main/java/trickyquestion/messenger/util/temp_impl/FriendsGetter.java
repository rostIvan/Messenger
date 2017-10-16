package trickyquestion.messenger.util.temp_impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.add_friend_screen.model.Friend;
import trickyquestion.messenger.add_friend_screen.model.IFriend;

public class FriendsGetter {
    public static List<IFriend> getFriends(int size) {
        final List<IFriend> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final IFriend friend = new Friend("Some name: " + (i+1), UUID.randomUUID(), null, i % 2 == 0);
            list.add(friend);
        }
        return list;
    }
}
