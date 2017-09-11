package trickyquestion.messenger.MainScreen.MainTabsContent.Interactors;

import java.util.List;

import trickyquestion.messenger.Util.Filters.FriendFilter;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;
import trickyquestion.messenger.MainScreen.MainTabsContent.Repository.FriendsRepository;

public class FriendListInteractor {
    private static List<Friend> friends;

    public static List<Friend> getFriends() {
        friends = FriendsRepository.getFriends();
        return friends;
    }
    public static List<Friend> getFriends(final String searchValue) {
        friends = FriendsRepository.getFriends();
        return FriendFilter.filter(friends, searchValue);
    }
}
