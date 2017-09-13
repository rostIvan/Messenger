package trickyquestion.messenger.add_friend_screen.presenter;


import android.view.ViewGroup;

import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;

public interface IAddFriendPresenter {
    void onCreate();


    AddFriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);
    void onBindViewHolder(final AddFriendViewHolder holder, final int position);
    int getCount();
}
