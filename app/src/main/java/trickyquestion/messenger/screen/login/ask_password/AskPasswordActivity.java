package trickyquestion.messenger.screen.login.ask_password;

import android.support.v4.app.Fragment;

import trickyquestion.messenger.screen.login.SingleFragmentActivity;

public class AskPasswordActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new AskPasswordFragment();
    }

}
