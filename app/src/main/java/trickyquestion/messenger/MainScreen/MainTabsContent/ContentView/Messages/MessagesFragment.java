package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters.RecyclerViewMessageAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages.IMessagePresenter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages.MessagePresenter;
import trickyquestion.messenger.MainScreen.View.Dialogs.FriendProfileView;
import trickyquestion.messenger.R;

public class MessagesFragment extends Fragment implements IMessageView {

    private IMessagePresenter presenter;

    @BindView(R.id.rv_messages)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private FriendProfileView friendProfileView;

    public static MessagesFragment newInstance() {
        final Bundle args = new Bundle();
        final MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        if (presenter == null) presenter = new MessagePresenter(this);
        presenter.onCreateView();
        return view;
    }

    @Override
    public void onStart() {
        presenter.onStart();
        Log.i("checkDebug", "start was: " + isFriendProfileOpen());
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
        Log.i("checkDebug", "save was: " + isFriendProfileOpen());
        super.onSaveInstanceState(outState);
    }

    @Override
    public Context getFragmentContext() {
        return getContext();
    }

    @Override
    public void showMessageContent() {
        final RecyclerViewMessageAdapter adapter = new RecyclerViewMessageAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setupSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(presenter.onRefreshListener());
        refreshLayout.setProgressBackgroundColorSchemeColor(presenter.getProgressBackgroundColor());
        refreshLayout.setColorSchemeColors(presenter.getSchemeColors());
    }

    @Override
    public void setRefreshing(boolean isRefresh) {
        refreshLayout.setRefreshing(isRefresh);
    }


    @Override
    public void showFriendProfile() {
        if (friendProfileView == null)
            friendProfileView = new FriendProfileView(getContext());
        friendProfileView.show();
        Log.i("checkDebug", "show!!! : ");
    }

    @Override
    public boolean isFriendProfileOpen() {
        Log.i("checkDebug", "bool : " + (friendProfileView == null));
        return friendProfileView != null && friendProfileView.isShow();
    }
}
