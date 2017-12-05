package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends;

import android.content.Context;
import android.content.Intent;
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
import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.view.ChatActivity;
import trickyquestion.messenger.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.RecyclerViewAdapters.RecyclerViewFriendAdapter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends.FriendPresenter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends.IFriendPresenter;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public class FriendsFragment extends Fragment implements IFriendsView {

    private IFriendPresenter presenter;

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;

    private SearchView searchView;

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
    public void showFriendProfile(String name, boolean online) {
        final FriendPhotoDialog dialog = FriendPhotoDialog.newInstance();
        final Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putBoolean("online", online);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "profile fragment");
    }

    @Override
    public String getSearchQuery() {
        return searchView != null ? searchView.getQuery().toString() : null;
    }

    @Override
    public void showChatActivity(final Friend friend) {
        final Intent i = new Intent(this.getContext(), ChatActivity.class);
        i.putExtra(ChatActivity.FRIEND_NAME_EXTRA, friend.getName());
        i.putExtra(ChatActivity.FRIEND_ID_EXTRA, friend.getId());
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.translate_left_slide, R.anim.alpha_to_zero);
    }

    @Override
    public void runOnUiThread(final Runnable r) {
        this.getActivity().runOnUiThread(r);
    }
}
