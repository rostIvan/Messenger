package trickyquestion.messenger.login_screen.authentication;


import android.support.v4.app.Fragment;

import trickyquestion.messenger.login_screen.SingleFragmentActivity;

public class LoginScreenActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }
}
