package trickyquestion.messenger.screen.login.authentication;


import android.support.v4.app.Fragment;

import trickyquestion.messenger.screen.login.SingleFragmentActivity;

public class LoginScreenActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }

}
