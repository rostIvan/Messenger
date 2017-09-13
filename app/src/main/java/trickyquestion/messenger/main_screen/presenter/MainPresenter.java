package trickyquestion.messenger.main_screen.presenter;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import trickyquestion.messenger.main_screen.view.IMainView;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private static boolean dialogWasOpened;

    public MainPresenter(final IMainView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        if (true) view.startLoginActivity();
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
                if (!view.isSearchViewIconified()) view.setSearchViewIconified(true);
                else view.goBack();
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
    public MenuItem.OnMenuItemClickListener onSettingClick() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                view.showDialogMenu();
                return true;
            }
        };
    }

    @Override
    public MenuItem.OnMenuItemClickListener onAccountMenuItemClick() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                view.showToast("Account item click");
                return true;
            }
        };
    }
}
