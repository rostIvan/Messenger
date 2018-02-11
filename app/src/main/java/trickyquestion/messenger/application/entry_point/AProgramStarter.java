package trickyquestion.messenger.application.entry_point;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public abstract class AProgramStarter {

    private Context context;
    private AuthPreference authPreference;

    abstract void start();

    protected void applyContext(final Context context) {
        this.context = context;
        this.authPreference = new AuthPreference(context);
    }

    protected void connectToNetwork() {
        P2PProtocolConnector.TryStart(context);
    }

    protected void startActivity(final Class<? extends Activity> activity) {
        final Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean askPasswordActivated() {
        return authPreference.askPassword();
    }

    public boolean isAuthenticated() {
        return authPreference.isUserAuthenticated();
    }
}
