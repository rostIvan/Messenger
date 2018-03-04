package trickyquestion.messenger.screen.tabs.friends.ui;

import java.util.List;

import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.abstraction.interfaces.BaseView;

public interface IFriendsView extends BaseView {
    void showFriends(List<Friend> friends);
    void updateFriends();
    void removeItem(int position, int size);
    void showFriendPhoto(Friend friend);
}
