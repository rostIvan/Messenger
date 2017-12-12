package trickyquestion.messenger.add_friend_screen.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.add_friend_screen.adapter.RecyclerViewAddFriendAdapter;
import trickyquestion.messenger.add_friend_screen.presenter.AddFriendPresenter;
import trickyquestion.messenger.add_friend_screen.presenter.IAddFriendPresenter;
import trickyquestion.messenger.R;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.preference.ThemePreference;
import trickyquestion.messenger.util.timer.SimpleCountDownTimer;

public class AddFriendActivity extends AppCompatActivity  implements IAddFriendView {
    @BindView(R.id.rv_add_friend)
    RecyclerView recyclerView;
    @BindView(R.id.add_friend_toolbar)
    Toolbar toolbar;
    @BindView(R.id.donut_progress)
    DonutProgress progress;

    private ThemePreference themePreference;
    private SimpleCountDownTimer timer;
    private IAddFriendPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new AddFriendPresenter(this);
        themePreference = new ThemePreference(this);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        settingMenuItem.setVisible(false);
        final MenuItem myAccount = menu.findItem(R.id.action_account);
        myAccount.setVisible(false);
        final MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        final MenuItem refresh = menu.findItem(R.id.action_refresh);
        refresh.setOnMenuItemClickListener((menuItem) -> presenter.onRefreshItemClick(menuItem));
        return true;
    }

    @Override
    public void customizeTheme() {
        toolbar.setBackgroundColor(themePreference.getPrimaryColor());
        progress.setUnfinishedStrokeColor(themePreference.getPrimaryColor());
        progress.setFinishedStrokeColor(Color.GRAY);
        progress.setTextColor(themePreference.getPrimaryColor());
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle(R.string.add_friend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onNavigationButtonPressed());
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showFriendsItems() {
        final RecyclerViewAddFriendAdapter adapter = new RecyclerViewAddFriendAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void notifyRecyclerDataChange() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showAddFriendAlertDialog(IUser user) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder
                .setTitle(String.format("Add user: %s? \nid: %s", user.getName(), user.getID().toString().substring(0, 25)))
                .setPositiveButton("Yes", (dialog, which) -> presenter.onAlertPositiveButtonPressed(user))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(themePreference.getPrimaryColor());
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(themePreference.getPrimaryColor());
    }

    @Override
    public void startTimer() {
        timer = new SimpleCountDownTimer(10_000, 100);
        timer.setAction(new SimpleCountDownTimer.CountDownTimerAction() {
            @Override
            public void onStart() {
                presenter.onProgressTimerStart();
            }

            @Override
            public void onProgress(long progressValue, long secondsUntilFinished) {
                presenter.onProgressTimer();
                progress.setText(String.valueOf(secondsUntilFinished));
                progress.setProgress(progressValue);
            }

            @Override
            public void onCancel() {
                presenter.onCancelTimer();
            }

            @Override
            public void onFinish() {
                presenter.onProgressTimerFinished();
            }
        });
        timer.startTimer();
    }

    @Override
    public View getProgressView() {
        final ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        return progressBar;
    }

    @Override
    public void cancelTimer() {
        timer.cancelTimer();
    }

    @Override
    public void showProgressBar() {
        recyclerView.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(final CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void runOnActivityUiThread(Runnable r) {
        super.runOnUiThread(r);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}
