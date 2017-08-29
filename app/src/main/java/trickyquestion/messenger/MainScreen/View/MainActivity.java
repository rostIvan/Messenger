package trickyquestion.messenger.MainScreen.View;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.ParallaxPageTransformer;
import com.eftimoff.viewpagertransformers.ZoomInTransformer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.Adapters.MainPagerAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.FriendsFragment;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages.MessagesFragment;
import trickyquestion.messenger.MainScreen.Presenter.IMainPresenter;
import trickyquestion.messenger.MainScreen.Presenter.MainPresenter;
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
    }

    @Override
    public void setupListeners() {
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

}
