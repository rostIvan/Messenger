package trickyquestion.messenger.MainScreen.MainTabsContent.Filters;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;

public class FriendFilter {
    public static List<Friend> filter(final List<Friend> friends, final String searchValue) {
        if (searchValue.isEmpty() || searchValue.length() == 0) return friends;
        final List<Friend> result = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getName().toLowerCase().contains(searchValue.toLowerCase()))
                result.add(friend);
        }
        return result;
    }
}
