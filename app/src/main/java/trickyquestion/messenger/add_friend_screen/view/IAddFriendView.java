package trickyquestion.messenger.add_friend_screen.view;


import android.content.Context;
import android.view.View;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public interface IAddFriendView {
    void customizeTheme();

    void customizeToolbar();

    void goBack();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    void cancelTimer();

    void showProgressBar();

    void hideProgressBar();

    void showToast(CharSequence text);

    void runOnActivityUiThread(Runnable r);

    void showAddFriendAlertDialog(IUser user);

    void startTimer();

    View getProgressView();

    Context getContext();
}
