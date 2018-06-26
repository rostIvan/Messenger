package trickyquestion.messenger.screen.login.sign_up;

import android.support.annotation.NonNull;

import java.util.UUID;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.ui.interfaces.BaseRouter.Screen;
import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.java.validation.UserInputValidator;

public class SignUpInteractor {
    private final SignUpFragment signUpFragment;
    private final AuthPreference authPreference;
    private final P2PConnector p2pConnector;

    public SignUpInteractor(@NonNull SignUpFragment signUpFragment,
                            @NonNull AuthPreference authPreference,
                            @NonNull P2PConnector p2pConnector) {
        this.signUpFragment = signUpFragment;
        this.authPreference = authPreference;
        this.p2pConnector = p2pConnector;
    }

    public void dataProcess(@NonNull String login, @NonNull String password) {
        if (dataEnteredCorrect(login, password)) createAccount(login, password);
        else showError();
    }

    private boolean dataEnteredCorrect(String login, String password) {
        return UserInputValidator.isLoginValid(login) && UserInputValidator.isPasswordValid(password);
    }


    private void createAccount(String login, String password) {
        saveAccount(login, password);
        p2pConnector.connect();
        signUpFragment.router().openScreen(Screen.MAIN);
        signUpFragment.router().finishActivity();
    }

    private void showError() {
        signUpFragment.showError("Sorry, but login and password must be longer that 3 digits");
    }

    private void saveAccount(String login, String password) {
        authPreference.setAccountId(UUID.randomUUID().toString());
        authPreference.setAccountData(login, password);
        authPreference.setUserAuthenticated(true);
    }
}
