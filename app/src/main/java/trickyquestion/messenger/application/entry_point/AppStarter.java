package trickyquestion.messenger.application.entry_point;

import android.content.Context;

public final class AppStarter extends AProgramStarter {

    public AppStarter(final Context context) {
        super.applyContext(context);
    }

    @Override
    public void start() {
        if ( !isAuthenticated() ) startLoginActivity();
        else {
            connectToNetwork();
            if ( askPasswordActivated() ) startAskPasswordActivity();
            else startMainActivity();
        }
    }
}
