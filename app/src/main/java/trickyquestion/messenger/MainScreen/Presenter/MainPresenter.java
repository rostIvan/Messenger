package trickyquestion.messenger.MainScreen.Presenter;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import trickyquestion.messenger.MainScreen.View.IMainView;
import trickyquestion.messenger.R;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private static boolean dialogWasOpened;

    public MainPresenter(final IMainView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showTabsWithContent();
        view.setPagerAnimation();
        if (dialogWasOpened) view.showDialogMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        dialogWasOpened = view.isDialogShow();
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            view.goBack();
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_MENU) {
            view.showDialogMenu();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem settingMenuItem = menu.findItem(R.id.action_menu);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this.onQueryTextListener());
        settingMenuItem.setOnMenuItemClickListener(this.onSettingClick());
        return true;
    }

    @Override
    public SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }

    @Override
    public MenuItem.OnMenuItemClickListener onSettingClick() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                view.showDialogMenu();
                return true;
            }
        };
    }
}
