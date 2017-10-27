package trickyquestion.messenger.main_screen.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import trickyquestion.messenger.main_screen.view.IMainView;
import trickyquestion.messenger.util.Constants;

import static android.app.Activity.RESULT_OK;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private final SharedPreferences preferences;

    public MainPresenter(final IMainView view) {
        this.view = view;
        this.preferences = view.getContext().getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showTabsWithContent();
        view.setPagerAnimation();
    }


    @Override
    public void onFinish() {
        setPasswordEntered(false);
    }

    private void setPasswordEntered(final boolean entered) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.EXTRA_PASSWORD_WAS_ENTER, entered);
        editor.apply();
        editor.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_AUTH) {
        if (resultCode != RESULT_OK) view.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
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
            view.showDialogMenu();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!view.isSearchViewIconified()) view.setSearchViewIconified(true);
        else view.goBack();
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

    @Override
    public ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                view.closeKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        };
    }

}
