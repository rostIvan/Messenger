package trickyquestion.messenger.screen.tabs.messages.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.main.container.implementation.MainActivity;
import trickyquestion.messenger.screen.main.tabs.messages.view.adapter.MessageViewHolder;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.tabs.messages.data.Message;
import trickyquestion.messenger.screen.tabs.messages.data.MessageUtil;
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter;
import trickyquestion.messenger.ui.abstraction.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.abstraction.fragment.AWithSearchFragment;
import trickyquestion.messenger.ui.abstraction.interfaces.Layout;

import static trickyquestion.messenger.util.ContextExtensionsKt.toast;
import static trickyquestion.messenger.util.ViewUtilKt.whiteColor;

@Layout(res = R.layout.fragment_messages)
public class MessagesFragment extends AWithSearchFragment implements IMessagesView {

    @BindView(R.id.rv_messages)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private IMessagesPresenter presenter = getPresenter();

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public MessagesPresenter getPresenter() {
        return new MessagesPresenter(this, ApplicationRouter.from(getContext()));
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setupSwipeRefreshLayout();
        addSearchListener(newText -> presenter.onQueryTextChanged(newText.toString()));
    }

    @Override
    public void showMessages(List<Message> messages) {
        recyclerView.setAdapter(new BaseRecyclerAdapter.Builder<Message, MessageViewHolder>()
                .holder(MessageViewHolder.class)
                .itemView(R.layout.item_messege)
                .items(messages)
                .bind(this::bindRecycler)
                .build());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void bindRecycler(Message model, MessageViewHolder holder, List<Message> items) {
        setupListValues(model, holder);
        setupListListeners(model, holder);
    }

    private void setupListValues(Message message, MessageViewHolder holder) {
        holder.message.setText(message.getText());
        holder.name.setText(message.getFriend().getName());
        holder.time.setText(MessageUtil.getTime(message));
    }

    private void setupListListeners(Message model, MessageViewHolder holder) {
        holder.itemView.setOnClickListener(view -> presenter.onMessageItemClick(model));
        holder.image.setOnClickListener(view -> presenter.onFriendPhotoClick(model));
    }

    @Override
    public void showProgress(boolean show) {
        if (getContext() == null) return;
        final String title = show ? "Updating..." : getString(R.string.app_name);
        refreshLayout.setRefreshing(show);
        setTitle(title);
    }

    @Override
    public void setTitle(String title) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void showFriendPhotoDialog(Bundle bundle) {
        final FriendPhotoDialog dialog = FriendPhotoDialog.newInstance(bundle);
        dialog.show(getFragmentManager(), "profile fragment");
    }

    @Override
    public void onUiThread(@NotNull Runnable runnable) { getActivity().runOnUiThread(runnable); }

    @Override
    public void showToast(@NotNull CharSequence text) { toast(this, text); }

    public void setupSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        refreshLayout.setProgressBackgroundColorSchemeColor(whiteColor(getContext()));
        refreshLayout.setColorSchemeColors(getThemePreference().getPrimaryColor());
    }

}
