package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends;

import android.content.Context;
import android.view.View;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

public interface IFriendsView {
    Context getFragmentContext();

    void showFriendsItems();

    void setFabBehavior();

    void notifyRecyclerDataChange();

    void hideFab();

    void showFab();

    void startAddFriendActivity();

    void showFriendProfile();

    boolean isFriendProfileOpen();

    void dismissPhotoDialog();

    String getSearchQuery();

    void showChatActivity(Friend message);
}
