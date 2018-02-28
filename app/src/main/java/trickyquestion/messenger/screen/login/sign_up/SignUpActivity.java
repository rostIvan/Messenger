package trickyquestion.messenger.screen.login.sign_up;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.screen.login.SingleFragmentActivity;
import trickyquestion.messenger.screen.main.container.implementation.MainActivity;

public class SignUpActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void startMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        P2PProtocolConnector.TryStart(this);
        finish();
    }

    public void saveAccountData(String login, String password) {
        getAuthPreference().setAccountId(UUID.randomUUID().toString());
        getAuthPreference().setAccountLogin(login);
        getAuthPreference().setAccountPassword(password);
    }
}
