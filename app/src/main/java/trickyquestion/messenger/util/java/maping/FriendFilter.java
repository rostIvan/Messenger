package trickyquestion.messenger.util.java.maping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;

public class FriendFilter {
    public static List<Friend> filter(final List<Friend> friends, final String searchValue) {
        if (searchValue.isEmpty() || searchValue.length() == 0) return friends;
        final List<Friend> result = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getName().toLowerCase(Locale.getDefault()).contains(searchValue.toLowerCase(Locale.getDefault())))
                result.add(friend);
        }
        return result;
    }
}
