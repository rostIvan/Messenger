package trickyquestion.messenger.main_screen.main_tabs_content.interactors;

import java.util.List;

import trickyquestion.messenger.util.filters.FriendFilter;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;

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
