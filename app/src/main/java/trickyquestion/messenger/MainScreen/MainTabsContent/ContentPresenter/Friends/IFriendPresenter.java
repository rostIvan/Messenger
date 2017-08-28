package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends;

import android.view.ViewGroup;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.FriendViewHolder;

public interface IFriendPresenter {
    void onCreateView();
    int getCount();
    void onBindViewHolder(final FriendViewHolder holder, final int position);
    FriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);
}
