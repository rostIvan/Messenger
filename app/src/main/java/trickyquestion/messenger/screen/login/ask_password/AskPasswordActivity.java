package trickyquestion.messenger.screen.login.ask_password;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import trickyquestion.messenger.screen.login.SingleFragmentActivity;
import trickyquestion.messenger.screen.main.view.MainActivity;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class AskPasswordActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new AskPasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public String getUserPassword() {
        return getAuthPreference().getAccountPassword();
    }

    public String getUserNick() {
        return getAuthPreference().getAccountLogin();
    }

    public void signInAccount() {
        getAuthPreference().setUserAuthenticated(true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
