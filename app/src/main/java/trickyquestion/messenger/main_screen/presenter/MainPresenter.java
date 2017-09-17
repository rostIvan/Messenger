package trickyquestion.messenger.main_screen.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Set;

import trickyquestion.messenger.login_screen.authentication.LoginFragment;
import trickyquestion.messenger.main_screen.view.IMainView;
import trickyquestion.messenger.main_screen.view.dialogs.SettingMenuDialog;

import static android.app.Activity.RESULT_OK;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private static boolean dialogWasOpened;

    private final SharedPreferences preferences;

    public static final String EXTRA_KEY_AUTH_DATA = "Auth date";
    public static final String EXTRA_KEY_AUTH_LOGIN = "Auth log";
    public static final String EXTRA_KEY_AUTH_PASSWORD = "Auth pass";
    public static final String EXTRA_KEY_IS_AUTHENTICATED = "Ask auth";

    public MainPresenter(final IMainView view) {
        this.view = view;
        preferences = view.getContext().getSharedPreferences(EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showTabsWithContent();
        view.setPagerAnimation();
        if (dialogWasOpened) view.showDialogMenu();
    }

    @Override
    public void onResume() {}

    @Override
    public void onAttachedToWindow() {
        if (!isAuthenticated()) view.startLoginActivity();
        else if (askPassword() && !passWasEnter()) view.startAskPassActivity();
    }

    private boolean askPassword() {
        return preferences.getBoolean(SettingMenuDialog.EXTRA_ASK_PASSWORD, false);
    }

    private boolean passWasEnter() {
        return preferences.getBoolean(SettingMenuDialog.EXTRA_PASSWORD_WAS_ENTER, false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setPasswordEntered(true);
    }

    @Override
    public void onFinish() {
        setPasswordEntered(false);
    }

    private void setPasswordEntered(final boolean entered) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SettingMenuDialog.EXTRA_PASSWORD_WAS_ENTER, entered);
        editor.apply();
        editor.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_AUTH) {
        if (resultCode != RESULT_OK) view.finish();
        else if (requestCode == REQUEST_AUTH && data != null) {
            final String login = data.getStringExtra(LoginFragment.EXTRA_TAG_AUTH_LOGIN);
            final String password = data.getStringExtra(LoginFragment.EXTRA_TAG_AUTH_PASS);
            if (login != null && password != null && !login.isEmpty() && !password.isEmpty()) {
                saveAccountDate(login, password);
                view.showToast("log: \t" + getLogin() + "\npass: \t" + getPassword());
            }
            else {
                view.showToast("Error");
                view.finish();
            }
        }
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

    private void saveAccountDate(final String login, final String password) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EXTRA_KEY_AUTH_LOGIN, login);
        editor.putString(EXTRA_KEY_AUTH_PASSWORD, password);
        editor.putBoolean(EXTRA_KEY_IS_AUTHENTICATED, true);

        editor.apply();
        editor.commit();
    }


    private String  getLogin() {
        final SharedPreferences preferences = view.getContext().getSharedPreferences(EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
        return preferences.getString(EXTRA_KEY_AUTH_LOGIN, null);
    }
    private String  getPassword() {final SharedPreferences preferences = view.getContext().getSharedPreferences(EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
        return preferences.getString(EXTRA_KEY_AUTH_PASSWORD, null);
    }
    private boolean isAuthenticated() {
        final SharedPreferences preferences = view.getContext().getSharedPreferences(EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
        if ( getLogin() != null && getPassword() != null)
            return true;
        return preferences.getBoolean(EXTRA_KEY_IS_AUTHENTICATED, false);
    }
}
