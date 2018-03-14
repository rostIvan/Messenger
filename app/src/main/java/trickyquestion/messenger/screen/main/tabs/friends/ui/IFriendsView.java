package trickyquestion.messenger.screen.main.tabs.friends.ui;

import android.os.Bundle;

import java.util.List;

import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.interfaces.BaseView;

public interface IFriendsView extends BaseView {
    void showFriends(List<Friend> friends);
    void updateFriends();
    void removeItem(int position, int size);
    void showFriendPhoto(Bundle bundle);
}
