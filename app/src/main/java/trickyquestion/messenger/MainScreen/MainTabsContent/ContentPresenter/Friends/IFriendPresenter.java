package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ViewGroup;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.FriendViewHolder;

public interface IFriendPresenter {
    void onCreateView();
    int getCount();
    void onBindViewHolder(final FriendViewHolder holder, final int position);
    FriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);
    SearchView.OnQueryTextListener onQueryTextListener();
}
