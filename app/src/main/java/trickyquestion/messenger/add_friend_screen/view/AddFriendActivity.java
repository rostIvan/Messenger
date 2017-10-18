package trickyquestion.messenger.add_friend_screen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import trickyquestion.messenger.add_friend_screen.adapter.RecyclerViewAddFriendAdapter;
import trickyquestion.messenger.add_friend_screen.presenter.AddFriendPresenter;
import trickyquestion.messenger.add_friend_screen.presenter.IAddFriendPresenter;
import trickyquestion.messenger.R;

public class AddFriendActivity extends AppCompatActivity  implements IAddFriendView {
    @BindView(R.id.rv_add_friend)
    RecyclerView recyclerView;
    @BindView(R.id.add_friend_toolbar)
    Toolbar toolbar;
    private SearchView searchView;

    private IAddFriendPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new AddFriendPresenter(this);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        settingMenuItem.setVisible(false);
        final MenuItem myAccount = menu.findItem(R.id.action_account);
        myAccount.setVisible(false);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    @Override
    public void customizeToolbar() {
        toolbar.setTitle("Invite friends");
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
    public boolean isSearchViewIconified() {
        return searchView.isIconified();
    }

    @Override
    public void setSearchViewIconified(final boolean iconified) {
        searchView.setIconified(iconified);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
