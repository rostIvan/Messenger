package trickyquestion.messenger.application.entry_point;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordActivity;
import trickyquestion.messenger.screen.login.authentication.LoginScreenActivity;
import trickyquestion.messenger.screen.main.view.MainActivity;
import trickyquestion.messenger.util.android.preference.AuthPreference;

abstract class AProgramStarter {

    private Context context;
    private AuthPreference authPreference;

    abstract void start();

    void applyContext(final Context context) {
        this.context = context;
        this.authPreference = new AuthPreference(context);
    }

    void startLoginActivity() {
        startActivity(LoginScreenActivity.class);
    }

    void startAskPasswordActivity() {
        startActivity(AskPasswordActivity.class);
    }

    void startMainActivity() {
        startActivity(MainActivity.class);
    }

    void connectToNetwork() {
        P2PProtocolConnector.TryStart(context);
    }

    boolean askPasswordActivated() {
        return authPreference.askPassword();
    }

    boolean isAuthenticated() {
        return authPreference.isUserAuthenticated();
    }

    private void startActivity(final Class<? extends Activity> activity) {
        final Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
