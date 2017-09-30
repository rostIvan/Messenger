package trickyquestion.messenger.chat_screen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.presenter.ChatPresenter;
import trickyquestion.messenger.chat_screen.presenter.IChatPresenter;

public class ChatActivity extends AppCompatActivity implements IChatView {
    @BindView(R.id.message_toolbar)
    Toolbar toolbar;
    private IChatPresenter presenter;

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
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onNavigationButtonPressed());
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
}
