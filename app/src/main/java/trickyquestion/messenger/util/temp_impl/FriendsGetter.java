package trickyquestion.messenger.util.temp_impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public class FriendsGetter {
    public static List<Friend> getFriends(int size) {
        final List<Friend> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Friend friend = new Friend("Some name: " + (i+1), UUID.randomUUID(), null, i % 2 == 0);
            list.add(friend);
        }
        return list;
    }
}
