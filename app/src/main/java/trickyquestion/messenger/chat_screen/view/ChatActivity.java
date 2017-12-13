package trickyquestion.messenger.chat_screen.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.swipebackfragment.SwipeBackActivity;
import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.adapters.RecyclerChatAdapter;
import trickyquestion.messenger.chat_screen.presenter.ChatPresenter;
import trickyquestion.messenger.chat_screen.presenter.IChatPresenter;
import trickyquestion.messenger.util.preference.ThemePreference;

public class ChatActivity extends SwipeBackActivity implements IChatView {

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
    public static final String FRIEND_ID_EXTRA = "friendId";

    private ThemePreference themePreference;

    private IChatPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new ChatPresenter(this);
        themePreference = new ThemePreference(this);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem settingsMenuItem = menu.findItem(R.id.clear_messages);
        final SpannableString s = new SpannableString(settingsMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(themePreference.getPrimaryColor()), 0, s.length(), 0);
        settingsMenuItem.setTitle(s);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_messages :
                presenter.onClearMessagesItemCLick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void customizeTheme() {
        toolbar.setBackgroundColor(themePreference.getPrimaryColor());
        sendButton.setColorFilter(themePreference.getPrimaryColor());
        ViewCompat.setBackgroundTintList(messageField, ColorStateList.valueOf(themePreference.getPrimaryColor()));
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle(getFriendName());
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
    public void runOnUIThread(final Runnable r) {
        super.runOnUiThread(r);
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

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFriendName() {
        return getIntent().getStringExtra(FRIEND_NAME_EXTRA);
    }

    @Override
    public String getFriendId() {
        return getIntent().getStringExtra(FRIEND_ID_EXTRA);
    }

    @Override
    public void setStyleForMyMessage(final View container, final TextView textMessage, final TextView timeMessage) {
        final Drawable shape = getResources().getDrawable(R.drawable.shape_my_message);
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        shape.setColorFilter(themePreference.getPrimaryColor(), PorterDuff.Mode.SRC_IN);
        params.setMargins(130, 20, 30, 20);
        container.setLayoutParams(params);
        container.setBackgroundDrawable(shape);
        textMessage.setTextColor(Color.WHITE);
        timeMessage.setTextColor(getResources().getColor(R.color.colorTransparentGray));
    }

    @Override
    public void setStyleForFriendMessage(final View container, final TextView textMessage, final TextView timeMessage) {
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 20, 130, 20);
        container.setLayoutParams(params);
        container.setBackgroundResource(R.drawable.shape_friend_message);
        textMessage.setTextColor(Color.BLACK);
        timeMessage.setTextColor(Color.argb(100, 0, 0, 0));
    }

}
