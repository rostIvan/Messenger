package trickyquestion.messenger.screen.login.ask_password;

import trickyquestion.messenger.ui.interfaces.BaseRouter.Screen;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class AskPasswordInteractor {
    private final AskPasswordFragment fragment;
    private final AuthPreference authPreference;

    public AskPasswordInteractor(AskPasswordFragment fragment, AuthPreference authPreference) {
        this.fragment = fragment;
        this.authPreference = authPreference;
    }

    public String getGreeting() {
        final String login = authPreference.getAccountLogin();
        final String password = authPreference.getAccountPassword();
        return String.format("Hello, %s pass[%s]", login, password);
    }

    public void dataProcess(String enteredPassword) {
        if (passEnteredCorrect(enteredPassword)) signInAccount();
        else showError();
    }

    private boolean passEnteredCorrect(String enteredPassword) {
        return authPreference.getAccountPassword().equals(enteredPassword);
    }

    private void signInAccount() {
        fragment.router().openScreen(Screen.MAIN);
        fragment.router().finishActivity();
    }

    private void showError() {
        fragment.showError("Sorry, but entered password is incorrect");
    }
}
