package trickyquestion.messenger.screen.login.sign_up;

import java.util.UUID;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.java.validation.SignUpValidator;

public class SignUpViewModel {
    private final ApplicationRouter router;
    private final AuthPreference authPreference;
    private final P2PConnector p2pConnector;

    public SignUpViewModel(ApplicationRouter router, AuthPreference authPreference, P2PConnector p2pConnector) {
        this.router = router;
        this.authPreference = authPreference;
        this.p2pConnector = p2pConnector;
    }

    public void onSignUpButtonClick(String login, String password, SignUpCallback callback) {
        if (login == null || password == null) throw new IllegalArgumentException("login or password are NULL");
        if (isEnteredCorrect(login, password)) callback.onSuccess();
        else callback.onError();
    }

    public void createAccount(String login, String password) {
        saveAccount(login, password);
        p2pConnector.connect();
        router.openScreen(BaseRouter.Screen.MAIN);
        router.finishActivity();
    }

    private void saveAccount(String login, String password) {
        authPreference.setAccountId(UUID.randomUUID().toString());
        authPreference.setAccountData(login, password);
        authPreference.setUserAuthenticated(true);
    }

    private boolean isEnteredCorrect(String login, String password) {
        return SignUpValidator.isCorrect(login, password);
    }

    public interface SignUpCallback {
        void onSuccess();
        void onError();
    }
}
