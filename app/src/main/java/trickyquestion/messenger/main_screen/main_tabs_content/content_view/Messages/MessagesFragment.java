package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.view.ChatActivity;
import trickyquestion.messenger.dialogs.FriendProfileView;
import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.RecyclerViewAdapters.RecyclerViewMessageAdapter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Messages.IMessagePresenter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Messages.MessagePresenter;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

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
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
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
    public void showChatActivity(final Message message) {
        final Intent i = new Intent(this.getContext(), ChatActivity.class);
        i.putExtra(ChatActivity.FRIEND_NAME_EXTRA, message.getNameSender());
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.translate_left_slide, R.anim.alpha_to_zero);
    }
}
