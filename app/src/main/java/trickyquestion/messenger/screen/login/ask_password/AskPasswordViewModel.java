package trickyquestion.messenger.screen.login.ask_password;


import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class AskPasswordViewModel {
    private final AuthPreference authPreference;
    private final ApplicationRouter router;

    public AskPasswordViewModel(ApplicationRouter router, AuthPreference authPreference) {
        this.router = router;
        this.authPreference = authPreference;
    }

    public void signInAccount() {
        router.openScreen(BaseRouter.Screen.MAIN);
        router.finishActivity();
    }

    public String getUserNick() {
        return authPreference.getAccountLogin();
    }

    public String getUserPassword() {
        return authPreference.getAccountPassword();
    }
}
