package trickyquestion.messenger.add_friend_screen.view;


import android.content.Context;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

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

    void showAddFriendAlertDialog(IUser user);

    void startTimer();

    Context getContext();

}
