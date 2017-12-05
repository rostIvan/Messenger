package trickyquestion.messenger.add_friend_screen.presenter;


import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;
import trickyquestion.messenger.add_friend_screen.model.IFriend;

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

    void onAlertPositiveButtonPressed(IFriend friend);
}
