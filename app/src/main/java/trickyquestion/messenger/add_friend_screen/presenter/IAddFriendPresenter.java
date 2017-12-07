package trickyquestion.messenger.add_friend_screen.presenter;


import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public interface IAddFriendPresenter {
    void onCreate();
    View.OnClickListener onNavigationButtonPressed();

    AddFriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);
    void onBindViewHolder(final AddFriendViewHolder holder, final int position);
    int getCount();

    void onProgressTimer();
    void onProgressTimerFinished();
    void onCancelTimer();
    void onProgressTimerStart();

    void onAlertPositiveButtonPressed(IUser user);
    boolean onRefreshItemClick(MenuItem menuItem);
}
