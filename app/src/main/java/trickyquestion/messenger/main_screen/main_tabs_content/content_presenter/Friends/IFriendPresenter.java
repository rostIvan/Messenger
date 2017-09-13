package trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.FriendViewHolder;

public interface IFriendPresenter {
    void onCreateView();
    int getCount();
    void onBindViewHolder(final FriendViewHolder holder, final int position);
    FriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);
    SearchView.OnQueryTextListener onQueryTextListener();
    void onSaveInstanceState(Bundle outState);
    void onStart();
    View.OnClickListener onFabClick();
}
