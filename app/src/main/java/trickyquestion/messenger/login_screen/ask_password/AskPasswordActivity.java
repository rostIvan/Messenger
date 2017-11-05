package trickyquestion.messenger.login_screen.ask_password;

import android.support.v4.app.Fragment;

import trickyquestion.messenger.login_screen.SingleFragmentActivity;

public class AskPasswordActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new AskPasswordFragment();
    }

}
