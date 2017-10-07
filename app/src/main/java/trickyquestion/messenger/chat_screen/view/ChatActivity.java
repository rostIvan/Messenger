package trickyquestion.messenger.chat_screen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.swipebackfragment.SwipeBackActivity;
import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.adapters.RecyclerChatAdapter;
import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.presenter.ChatPresenter;
import trickyquestion.messenger.chat_screen.presenter.IChatPresenter;
import trickyquestion.messenger.util.formatter.TimeFormatter;

public class ChatActivity extends SwipeBackActivity implements IChatView {
    private IChatPresenter presenter;

    @BindView(R.id.message_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_chat)
    RecyclerView recyclerView;
    @BindView(R.id.message_field)
    EditText messageField;
    @BindView(R.id.send_button)
    ImageButton sendButton;
    @BindView(R.id.attach_button)
    ImageButton attachButton;

    public static final String FRIEND_NAME_EXTRA = "friendName";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new ChatPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle(getIntent().getStringExtra(FRIEND_NAME_EXTRA));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setupListeners() {
        toolbar.setNavigationOnClickListener(presenter.onNavigationButtonPressed());
        sendButton.setOnClickListener(presenter.onSendButtonClick());
    }

    @Override
    public void showMessages() {
        final RecyclerChatAdapter adapter = new RecyclerChatAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.alpha_to_one, R.anim.translate_right_slide);
    }
    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void refreshRecycler() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    @Override
    public void scrollRecyclerToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }
    @Override
    public String getMessageText() {
        return messageField.getText().toString().trim();
    }
    @Override
    public void clearMessageText() {
        messageField.setText("");
    }
}
