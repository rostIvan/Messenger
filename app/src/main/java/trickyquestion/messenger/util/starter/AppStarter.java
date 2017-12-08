package trickyquestion.messenger.util.starter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import trickyquestion.messenger.login_screen.ask_password.AskPasswordActivity;
import trickyquestion.messenger.login_screen.authentication.LoginScreenActivity;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.main_screen.view.MainActivity;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.util.preference.AuthPreference;

public class AppStarter implements IStarter {

    private final Context context;
    private final AuthPreference authPreference;

    public AppStarter(final Context context) {
        this.context = context;
        this.authPreference = new AuthPreference(context);
    }

    @Override
    public void start() {
        if ( !isAuthenticated() ) {
            startLoginActivity();
            FriendsRepository.addFriend(new Friend());
        }
        else {
            P2PProtocolConnector.TryStart(context);
            if ( askPasswordActivated() ) startAskPasswordActivity();
            else startMainActivity();
        }
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
        return authPreference.askPassword();
    }

    private boolean isAuthenticated() {
        return authPreference.isUserAuthenticated();
    }
}
