package trickyquestion.messenger.MainScreen.MainTabsContent.Repository;

import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;

public class FriendsRepository {

    private static List<Friend> friends;

    public static List<Friend> getFriends() {
        friends = Friend.getFriends(40);
        return friends;
    }

}
