package trickyquestion.messenger.screen.chat.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.data.MessageUtil;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.activity.BaseChatActivity;
import trickyquestion.messenger.ui.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.interfaces.Layout;
import trickyquestion.messenger.ui.util.ViewUtilKt;

@Layout(res = R.layout.activity_chat_message)
public class ChatActivity extends BaseChatActivity implements IChatView {
    @BindView(R.id.chat_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_chat)
    RecyclerView recyclerView;
    @BindView(R.id.message_field)
    EditText messageField;
    @BindView(R.id.send_button)
    ImageButton sendButton;

    public static final String FRIEND_NAME_EXTRA = "friendName";
    public static final String FRIEND_ID_EXTRA = "friendId";

    final ChatPresenter presenter = new ChatPresenter(this, ApplicationRouter.from(this));

    @Override
    public ChatPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.connectToFriend(new Friend(getFriendName(), getFriendId()));
        setupListeners();
        setupTheme();
    }

    private void setupListeners() {
        sendButton.setOnClickListener(view -> {
            final String message = messageField.getText().toString();
            presenter.onSendMessageClick(message);
        });
        recyclerView.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) scrollToEnd();
        });
    }

    private void setupTheme() {
        getSupportActionBar().setTitle("  " + getFriendName());
        getSupportActionBar().setLogo(R.drawable.ic_user_white);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        sendButton.setColorFilter(getThemePreference().getPrimaryColor());
        ViewCompat.setBackgroundTintList(messageField, ColorStateList.valueOf(getThemePreference().getPrimaryColor()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_messages : presenter.onClearMessagesItemCLick(); return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showChatMessages(List<ChatMessage> messages) {
        recyclerView.setAdapter(new BaseRecyclerAdapter.Builder<ChatMessage, ChatViewHolder>()
                .holder(ChatViewHolder.class)
                .items(messages)
                .itemView(R.layout.item_chat_messege)
                .bind(this::bindRecycler)
                .build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindRecycler(ChatMessage message, ChatViewHolder holder, List<ChatMessage> items) {
        holder.textMessage.setText(message.getText());
        holder.timeMessage.setText(MessageUtil.getTime(message));
        if (message.isMine()) ViewUtilKt.setMyMessageStyle(this, message, holder);
        else ViewUtilKt.setFriendMessageStyle(this, message, holder);
    }

    @Override
    public void updateMessages() {
        scrollToEnd();
    }

    @Override
    public void clearInputText() {
        messageField.setText("");
    }

    @Override
    public void hideSendButton() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_to_zero);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) { sendButton.setVisibility(View.GONE); }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        sendButton.startAnimation(animation);
    }

    @Override
    public void showSendButton() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_to_one);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) { sendButton.setVisibility(View.VISIBLE); }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        sendButton.startAnimation(animation);
    }

    private void scrollToEnd() {
        final int endPosition = recyclerView.getAdapter().getItemCount() - 1;
        recyclerView.postDelayed(() -> recyclerView.scrollToPosition(endPosition), 50);
    }

    @NonNull
    @Override
    protected Toolbar getToolbar() { return toolbar; }

    private String getFriendName() {
        return getIntent().getStringExtra(FRIEND_NAME_EXTRA);
    }

    private UUID getFriendId() {
        final String id = getIntent().getStringExtra(FRIEND_ID_EXTRA);
        return UUID.fromString(id);
    }
}
