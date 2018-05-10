package trickyquestion.messenger.util.android.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;

import io.realm.Realm;
import trickyquestion.messenger.util.Constants;

public class AuthPreference {

    private final SharedPreferences preferences;

    public AuthPreference(final Context context) {
        this.preferences = context
                .getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
    }

    public String getAccountLogin() {
        return preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN, "_NULL_");
    }

    public String getAccountId() {
        return preferences.getString(Constants.EXTRA_KEY_USER_ID, "_NAN_");
    }

    public void setAccountData(final String login, final String password, final String encKey) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_LOGIN, login);
        editor.putString(Constants.EXTRA_KEY_AUTH_PASSWORD, password);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, true);
        editor.apply();
        editor.commit();
    }

    public void setAccountLogin(final String login) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_LOGIN, login);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, true);
        editor.apply();
        editor.commit();
    }

    public void setAccountPassword(final String password) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_PASSWORD, password);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, true);
        editor.apply();
        editor.commit();
    }

    public void clearAccountData() {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_LOGIN, null);
        editor.putString(Constants.EXTRA_KEY_AUTH_PASSWORD, null);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, false);
        editor.apply();
        editor.commit();
    }

    public boolean askPassword() {
        return preferences.getBoolean(Constants.EXTRA_ASK_PASSWORD, false);
    }

    public void setAskingPassword(boolean askingPassword) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.EXTRA_ASK_PASSWORD, askingPassword);
        editor.apply();
        editor.commit();
    }

    public String getAccountPassword() {
        return preferences.getString(Constants.EXTRA_KEY_AUTH_PASSWORD, "someone pass");
    }

    public void setUserAuthenticated(boolean userAuthenticated) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, userAuthenticated);
        editor.apply();
        editor.commit();
    }

    public void setAccountId(String id) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_USER_ID, id);
        editor.apply();
        editor.commit();
    }

    public boolean isUserAuthenticated() {
        return preferences.getBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, false);
    }

    public void setShowNotifications(boolean showNotifications) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.EXTRA_SHOW_NOTIFICATIONS, showNotifications);
        editor.apply();
        editor.commit();
    }

    public boolean showNotifications() {
        return preferences.getBoolean(Constants.EXTRA_SHOW_NOTIFICATIONS, false);
    }
}
    