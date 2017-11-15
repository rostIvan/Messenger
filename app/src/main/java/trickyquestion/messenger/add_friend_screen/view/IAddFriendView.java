package trickyquestion.messenger.add_friend_screen.view;


import android.content.Context;

public interface IAddFriendView {
    void customizeTheme();

    void customizeToolbar();

    void goBack();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    boolean isSearchViewIconified();

    void setSearchViewIconified(boolean iconified);

    void runOnActivityUiThread(Runnable r);

    Context getContext();

}
