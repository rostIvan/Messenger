package trickyquestion.messenger.screen.main.main_tabs_content.friends.interactors;

import java.util.List;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.repository.FriendsRepository;
import trickyquestion.messenger.util.java.maping.FriendFilter;

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
