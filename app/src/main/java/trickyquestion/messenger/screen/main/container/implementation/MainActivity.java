package trickyquestion.messenger.screen.main.container.implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.di.PresentationFactory;
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter;
import trickyquestion.messenger.screen.main.container.interfaces.IMainView;
import trickyquestion.messenger.screen.popup_windows.AccountPopup;
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsFragment;
import trickyquestion.messenger.screen.main.tabs.messages.ui.MessagesFragment;
import trickyquestion.messenger.ui.activity.AWithToolbarActivity;
import trickyquestion.messenger.ui.interfaces.Layout;
import trickyquestion.messenger.ui.interfaces.BasePresenter;
import trickyquestion.messenger.util.Constants;

import static butterknife.internal.Utils.listOf;
import static trickyquestion.messenger.ui.util.ViewUtilKt.applyThemeColor;

@Layout(res = Constants.MAIN_LAYOUT)
public class MainActivity extends AWithToolbarActivity implements IMainView {

    @BindView(R.id.header)
    AppBarLayout appBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager_tab)
    SmartTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    SearchView searchView;
    AccountPopup accountPopup;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();
        setupListeners();
    }

    private void applyTheme() {
        applyThemeColor(listOf(appBar, tabLayout, fab), themePreference.getPrimaryColor());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setupListeners() {
        fab.setOnClickListener( v -> presenter.onFabClick() );
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageSelected(int position) { presenter.onPageSelected(position); }
        });
    }

    private void setupMenuListeners(MenuItem settingMenuItem, MenuItem accountMenuItem) {
        settingMenuItem.setOnMenuItemClickListener(menuItem -> {
            presenter.onSettingsClick();
            return true;
        });
        accountMenuItem.setOnMenuItemClickListener(menuItem -> {
            presenter.onAccountClick();
            return true;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final boolean keyDown = super.onKeyDown(keyCode, event);
        presenter.onKeyDown(keyCode, event);
        return keyDown;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        final MenuItem accountMenuItem = menu.findItem(R.id.action_account);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        setupMenuListeners(settingMenuItem, accountMenuItem);
        return true;
    }

    @Override
    public void showContent() {
        final MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFragment(FriendsFragment.newInstance(), R.string.friends);
        pagerAdapter.addFragment(MessagesFragment.newInstance(), R.string.messages);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
        viewPager.setPageTransformer(true, new AccordionTransformer());
    }

    @Override
    public void displayAccountPopup(boolean display) {
        accountPopup = new AccountPopup(this);
        if (display) accountPopup.show();
        else if (accountPopup.isShowing()) accountPopup.dismiss();
    }

    @Override
    public void displayFab(boolean display) {
        if ( display && !fab.isVisible() ) fab.show();
        else fab.hide();
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) searchView.onActionViewCollapsed();
        if (accountPopup != null && accountPopup.isShowing()) accountPopup.dismiss();
        else super.onBackPressed();
    }

    @Override
    public void refreshTheme() {
        applyThemeColor(listOf(toolbar, appBar, tabLayout, fab), themePreference.getPrimaryColor());
    }

    @NonNull
    @Override
    public BasePresenter getPresenter() {
        presenter = PresentationFactory.INSTANCE.create(this);
        return presenter;
    }

    @NonNull
    @Override
    protected Toolbar getToolbar() { return toolbar; }
}
