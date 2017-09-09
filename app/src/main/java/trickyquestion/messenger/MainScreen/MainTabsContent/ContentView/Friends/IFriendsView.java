package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends;

import android.content.Context;

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
}
