package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.add_friend_screen.view.AddFriendActivity;
import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.RecyclerViewAdapters.RecyclerViewFriendAdapter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends.FriendPresenter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends.IFriendPresenter;
import trickyquestion.messenger.dialogs.FriendProfileView;
import trickyquestion.messenger.R;

public class FriendsFragment extends Fragment implements IFriendsView {

    private IFriendPresenter presenter;

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private SearchView searchView;
    private FriendProfileView friendProfileView;

    public static FriendsFragment newInstance() {
        final Bundle args = new Bundle();
        final FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        if (presenter == null) presenter = new FriendPresenter(this);
        presenter.onCreateView();
        return view;
    }

    @Override
    public void onStart() {
        presenter.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(presenter.onQueryTextListener());
        if (presenter.getStackQuery() != null && !presenter.getStackQuery().isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(presenter.getStackQuery(), false);
            searchView.clearFocus();
        }
        searchView.setOnCloseListener(presenter.onCloseSearchViewListener());
    }

    @Override
    public Context getFragmentContext() {
        return getContext();
    }

    @Override
    public void showFriendsItems() {
        final RecyclerViewFriendAdapter adapter = new RecyclerViewFriendAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void notifyRecyclerDataChange() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setFabBehavior() {
        fab.setOnClickListener(presenter.onFabClick());
        fab.attachToRecyclerView(recyclerView);
    }

    @Override
    public void hideFab() {
        if (fab.getVisibility() == View.VISIBLE)
            fab.hide();
    }

    @Override
    public void showFab() {
    if (fab.getVisibility() != View.VISIBLE)
        fab.show();
    }

    @Override
    public void startAddFriendActivity() {
        final Intent intent = new Intent(getContext(), AddFriendActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showFriendProfile() {
        if (friendProfileView == null)
            friendProfileView = new FriendProfileView(getContext());
        friendProfileView.show();
    }

    @Override
    public boolean isFriendProfileOpen() {
        return friendProfileView != null && friendProfileView.isShowing();
    }

    @Override
    public void dismissPhotoDialog() {
        if (friendProfileView != null && friendProfileView.isShowing()) {
            friendProfileView.dismiss();
        }
    }

    @Override
    public String getSearchQuery() {
        return searchView != null ? searchView.getQuery().toString() : null;
    }
}
