package trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.FriendViewHolder;

public interface IFriendPresenter {
    void onCreateView();
    void onSaveInstanceState(Bundle outState);
    void onActivityCreated(Bundle savedInstanceState);
    void onStart();
    void onResume();

    int getCount();
    void onBindViewHolder(final FriendViewHolder holder, final int position);
    FriendViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);

    String getStackQuery();
    SearchView.OnQueryTextListener onQueryTextListener();
    View.OnClickListener onFabClick();
    SearchView.OnCloseListener onCloseSearchViewListener();
}
