package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters.RecyclerViewFriendAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.FriendPresenter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.IFriendPresenter;
import trickyquestion.messenger.MainScreen.View.Dialogs.FriendProfileView;
import trickyquestion.messenger.R;

public class FriendsFragment extends Fragment implements IFriendsView {

    private IFriendPresenter presenter;

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;
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
    public void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(presenter.onQueryTextListener());
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
}
