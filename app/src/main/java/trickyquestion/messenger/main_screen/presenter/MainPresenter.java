package trickyquestion.messenger.main_screen.presenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import trickyquestion.messenger.main_screen.view.IMainView;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;

    public MainPresenter(final IMainView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showTabsWithContent();
        view.setPagerAnimation();
        view.setFabBehavior();
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (view.isFabShow()) outState.putBoolean("fab was shown", true);
        else outState.putBoolean("fab was shown", false);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean("fab was shown")) view.showFab();
        else view.hideFab();
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onBackPressed();
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_MENU) {
            view.showSettingMenu();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!view.isSearchViewIconified()) view.setSearchViewIconified(true);
        else if (view.isAccountPopupShowing()) view.closeAccountPopup();
        else view.goBack();
    }

    @Override
    public MenuItem.OnMenuItemClickListener onSettingClick() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                view.showSettingMenu();
                return true;
            }
        };
    }

    @Override
    public MenuItem.OnMenuItemClickListener onAccountMenuItemClick() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                view.showAccountPopup();
                return true;
            }
        };
    }

    @Override
    public ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == 0) view.showFab();
                if (position == 1) view.hideFab();
                view.closeKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        };
    }

    @Override
    public View.OnClickListener onFabClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startAddFriendActivity();
            }
        };
    }

}
