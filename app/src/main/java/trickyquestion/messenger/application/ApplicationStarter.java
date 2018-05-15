package trickyquestion.messenger.application;

import android.support.annotation.NonNull;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class ApplicationStarter {
    private final AuthPreference authPreference;
    private final ApplicationRouter router;
    private final P2PConnector p2pConnector;

    public ApplicationStarter(@NonNull AuthPreference authPreference,
                              @NonNull ApplicationRouter router,
                              @NonNull P2PConnector p2pConnector) {
        this.authPreference = authPreference;
        this.router = router;
        this.p2pConnector = p2pConnector;
    }

    public void start() {
        if ( !isAuthenticated() ) signUp();
        else {
            connect();
            if ( askPasswordActivated() ) askPassword();
            else main();
        }
    }

    private void signUp() {
        router.openScreen(BaseRouter.Screen.SIGN_UP);
    }
    private void askPassword() {
        router.openScreen(BaseRouter.Screen.ASK_PASSWORD);
    }
    private void main() {
        router.openScreen(BaseRouter.Screen.MAIN);
    }

    private void connect() {
        p2pConnector.connect();
    }

    private boolean askPasswordActivated() {
        return authPreference.askPassword();
    }
    private boolean isAuthenticated() {
        return authPreference.isUserAuthenticated();
    }
}
