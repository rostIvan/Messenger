package trickyquestion.messenger.add_friend_screen.view;


import android.content.Context;

import trickyquestion.messenger.add_friend_screen.model.IFriend;

public interface IAddFriendView {
    void customizeTheme();

    void customizeToolbar();

    void goBack();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    boolean isSearchViewIconified();

    void setSearchViewIconified(boolean iconified);

    void cancelTimer();

    void showProgressBar();

    void hideProgressBar();

    void showToast(CharSequence text);

    void runOnActivityUiThread(Runnable r);

    void showAddFriendAlertDialog(IFriend friend);

    void startTimer();

    Context getContext();

}
