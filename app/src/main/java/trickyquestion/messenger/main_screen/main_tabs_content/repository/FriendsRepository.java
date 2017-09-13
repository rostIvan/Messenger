package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public class FriendsRepository {
    private static List<Friend> friends;

    public static List<Friend> getFriends() {
        if (friends == null)
            friends = new ArrayList<>();
        return friends;
    }

    public static void setFriends(List<Friend> friends) {
        FriendsRepository.friends = friends;
    }

    public static List<Friend> createRandom(int size) {
        return Friend.getFriends(size);
    }

    public static void addFriend(final Friend friend) {
        friends.add(friend);
    }
}
