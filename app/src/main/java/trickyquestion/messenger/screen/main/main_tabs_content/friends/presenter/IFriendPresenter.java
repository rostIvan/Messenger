package trickyquestion.messenger.screen.main.main_tabs_content.friends.presenter;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ViewGroup;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.view.adapter.FriendViewHolder;

public interface IFriendPresenter {
    void onCreateView();
    void onSaveInstanceState(Bundle outState);
    void onActivityCreated(Bundle savedInstanceState);
    void onStart();
    void onDestroy();

    int getCount();
    void onBindViewHolder(final FriendViewHolder holder, final int position);
    FriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);

    String getStackQuery();
    SearchView.OnQueryTextListener onQueryTextListener();
    SearchView.OnCloseListener onCloseSearchViewListener();
}
