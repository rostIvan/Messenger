package trickyquestion.messenger.util.starter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import trickyquestion.messenger.dialogs.SettingMenuDialog;
import trickyquestion.messenger.login_screen.ask_password.AskPasswordActivity;
import trickyquestion.messenger.login_screen.authentication.LoginScreenActivity;
import trickyquestion.messenger.main_screen.view.MainActivity;
import trickyquestion.messenger.util.Constants;

public class AppStarter implements IStarter {

    private final Context context;
    private final SharedPreferences preferences;

    public AppStarter(final Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
    }

    @Override
    public void start() {
        if ( !isAuthenticated() ) startLoginActivity();
        else if ( askPasswordActivated() ) startAskPasswordActivity();
        else startMainActivity();
    }

    private void startLoginActivity() {
        startActivity(LoginScreenActivity.class);
    }

    private void startAskPasswordActivity() {
        startActivity(AskPasswordActivity.class);
    }

    private void startMainActivity() {
        startActivity(MainActivity.class);
    }

    private void startActivity(final Class<? extends Activity> activity) {
        final Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean askPasswordActivated() {
        return preferences.getBoolean(SettingMenuDialog.EXTRA_ASK_PASSWORD, false);
    }

    private boolean isAuthenticated() {
        return preferences.getBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, false);
    }
}
