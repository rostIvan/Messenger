package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends;

import android.content.Context;
import android.view.View;

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

//    boolean isFragmentShow();

    String getSearchQuery();
}
