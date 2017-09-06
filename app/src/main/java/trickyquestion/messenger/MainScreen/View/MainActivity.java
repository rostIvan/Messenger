package trickyquestion.messenger.MainScreen.View;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.Adapters.MainPagerAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.FriendsFragment;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages.MessagesFragment;
import trickyquestion.messenger.MainScreen.Presenter.IMainPresenter;
import trickyquestion.messenger.MainScreen.Presenter.MainPresenter;
import trickyquestion.messenger.MainScreen.View.Menu.SettingMenuDialog;
import trickyquestion.messenger.R;
import trickyquestion.messenger.Util.Constants;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_pager_tab)
    SmartTabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private IMainPresenter presenter;
    private SettingMenuDialog dialogMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Constants.MAIN_LAYOUT);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new MainPresenter(this);
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
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showTabsWithContent() {
        final MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        settingMenuItem.setOnMenuItemClickListener(presenter.onSettingClick());
        return true;
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

    public boolean isDialogShow() {
        return dialogMenu != null && dialogMenu.isShow();
    }
}
