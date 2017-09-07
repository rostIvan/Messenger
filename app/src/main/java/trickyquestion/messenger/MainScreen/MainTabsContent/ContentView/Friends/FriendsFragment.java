package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters.RecyclerViewFriendAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.FriendPresenter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.IFriendPresenter;
import trickyquestion.messenger.R;

public class FriendsFragment extends Fragment implements IFriendsView {

    private IFriendPresenter presenter;

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;

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
    public void showFriendsItem() {
        final RecyclerViewFriendAdapter adapter = new RecyclerViewFriendAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void notifyRecyclerDataChange() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
