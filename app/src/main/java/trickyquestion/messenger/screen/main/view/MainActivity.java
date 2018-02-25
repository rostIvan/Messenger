package trickyquestion.messenger.screen.main.view;

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
import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.add_friend.ui.AddFriendActivity;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.view.FriendsFragment;
import trickyquestion.messenger.screen.main.main_tabs_content.messages.view.MessagesFragment;
import trickyquestion.messenger.screen.main.presenter.IMainPresenter;
import trickyquestion.messenger.screen.main.presenter.MainPresenter;
import trickyquestion.messenger.screen.main.view.adapter.MainPagerAdapter;
import trickyquestion.messenger.screen.popup_windows.AccountPopup;
import trickyquestion.messenger.screen.settings.view.SettingActivity;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.preference.ThemePreference;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_pager_tab)
    SmartTabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private AccountPopup accountPopup;
    private SearchView searchView;

    private IMainPresenter presenter;
    private ThemePreference themePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Constants.MAIN_LAYOUT);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new MainPresenter(this);
        this.themePreference = new ThemePreference(this);
        presenter.onCreate();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem refresh = menu.findItem(R.id.action_refresh);
        refresh.setVisible(false);
        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        settingMenuItem.setOnMenuItemClickListener(presenter.onSettingClick());
        final MenuItem myAccount = menu.findItem(R.id.action_account);
        myAccount.setOnMenuItemClickListener(presenter.onAccountMenuItemClick());
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    @Override
    public void customizeTheme() {
        appBar.setBackgroundColor(themePreference.getPrimaryColor());
        toolbar.setBackgroundColor(themePreference.getPrimaryColor());
        fab.setColorNormal(themePreference.getPrimaryColor());
        tabLayout.setBackgroundColor(themePreference.getPrimaryColor());
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onNavigationButtonPressed());
    }

    @Override
    public void setFabBehavior() {
        fab.setOnClickListener(presenter.onFabClick());
        viewPager.addOnPageChangeListener(presenter.onPageChangeListener());
    }

    @Override
    public void hideFab() {
        fab.hide();
    }

    @Override
    public void showFab() {
        fab.show();
    }

    @Override
    public boolean isFabShow() {
        return fab.isVisible();
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
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void setPagerAnimation() {
        viewPager.setPageTransformer(true, new AccordionTransformer());
    }

    @Override
    public void startAddFriendActivity() {
        final Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSettingMenu() {
        final Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
//        overridePendingTransition(R.anim.translate_top_side, R.anim.translate_bottom_side);
        overridePendingTransition(R.anim.translate_top_side, R.anim.alpha_to_zero);
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
    public boolean isSearchViewIconified() {
        return searchView == null || searchView.isIconified();
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
    public void showAccountPopup() {
        accountPopup = new AccountPopup(this);
        accountPopup.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeAccountPopup();
    }

    @Override
    public void closeAccountPopup() {
        if (accountPopup != null)
            accountPopup.dismiss();
    }

    @Override
    public boolean isAccountPopupShowing() {
        return accountPopup!= null && accountPopup.isShowing();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
