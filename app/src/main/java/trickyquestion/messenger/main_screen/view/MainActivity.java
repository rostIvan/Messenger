package trickyquestion.messenger.main_screen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.dialogs.SettingMenuDialog;
import trickyquestion.messenger.login_screen.ask_password.AskPasswordActivity;
import trickyquestion.messenger.login_screen.authentication.LoginScreenActivity;
import trickyquestion.messenger.main_screen.adapter.MainPagerAdapter;
import trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends.FriendsFragment;
import trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages.MessagesFragment;
import trickyquestion.messenger.main_screen.presenter.IMainPresenter;
import trickyquestion.messenger.main_screen.presenter.MainPresenter;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.util.Constants;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_pager_tab)
    SmartTabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    AppBarLayout appBar;

    private IMainPresenter presenter;
    private SettingMenuDialog dialogMenu;
    private SearchView searchView;

    private final int REQUEST_AUTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Constants.MAIN_LAYOUT);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new MainPresenter(this);
        presenter.onCreate();
        P2PProtocolConnector.TryStart(this.getContext());
    }

    @Override
    public void finish() {
        presenter.onFinish();
        super.finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void startLoginActivity() {
        final Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivityForResult(intent, REQUEST_AUTH);
    }

    @Override
    public void startAskPassActivity() {
        final Intent intent = new Intent(this, AskPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        settingMenuItem.setOnMenuItemClickListener(presenter.onSettingClick());

        final MenuItem myAccount = menu.findItem(R.id.action_account);
        myAccount.setOnMenuItemClickListener(presenter.onAccountMenuItemClick());

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data, REQUEST_AUTH);
        P2PProtocolConnector.TryStart(this.getContext());
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onNavigationButtonPressed());
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showTabsWithContent() {
        final MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),
                getApplicationContext());
        pagerAdapter.addFragment(FriendsFragment.newInstance(), R.string.friends);
        pagerAdapter.addFragment(MessagesFragment.newInstance(), R.string.messages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(presenter.onPageChangeListener());
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void setPagerAnimation() {
        viewPager.setPageTransformer(true, new AccordionTransformer());
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogMenu() {
        if (dialogMenu == null)
            dialogMenu = new SettingMenuDialog(this);
        dialogMenu.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return presenter.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean isDialogShow() {
        return dialogMenu != null && dialogMenu.isShow();
    }

    @Override
    public boolean isSearchViewIconified() {
        return searchView.isIconified();
    }

    @Override
    public void setSearchViewIconified(final boolean iconified) {
        searchView.setIconified(iconified);
    }

    @Override
    public void closeKeyboard() {
        final View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}

