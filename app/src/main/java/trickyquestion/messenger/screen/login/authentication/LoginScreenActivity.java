package trickyquestion.messenger.screen.login.authentication;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.screen.login.SingleFragmentActivity;
import trickyquestion.messenger.screen.main.view.MainActivity;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class LoginScreenActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }

    private AuthPreference authPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        authPreference = new AuthPreference(this);
    }

    public void startMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        P2PProtocolConnector.TryStart(this);
        finish();
    }

    public void saveAccountData(String login, String password) {
        authPreference.setAccountId(UUID.randomUUID().toString());
        authPreference.setAccountLogin(login);
        authPreference.setAccountPassword(password);
    }
}
