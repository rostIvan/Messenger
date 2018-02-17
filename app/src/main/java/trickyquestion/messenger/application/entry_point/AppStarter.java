package trickyquestion.messenger.application.entry_point;

import android.content.Context;

import trickyquestion.messenger.screen.login.ask_password.AskPasswordActivity;
import trickyquestion.messenger.screen.login.authentication.LoginScreenActivity;
import trickyquestion.messenger.screen.main.view.MainActivity;

public  class AppStarter extends AProgramStarter {


    public AppStarter(final Context context) {
        super.applyContext(context);
    }

    @Override
    public void start() {
        if ( !isAuthenticated() ) login();
        else {
            connect();
            if ( askPasswordActivated() ) askPassword();
            else main();
        }
    }

    public void login() {
        super.startActivity(LoginScreenActivity.class);
    }

    public void askPassword() {
        super.startActivity(AskPasswordActivity.class);
    }

    public void main() {
        super.startActivity(MainActivity.class);
    }

    public void connect() {
        super.connectToNetwork();
    }

}
