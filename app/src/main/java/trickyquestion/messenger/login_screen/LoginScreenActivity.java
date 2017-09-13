package trickyquestion.messenger.login_screen;


import android.content.Intent;
import android.support.v4.app.Fragment;

public class LoginScreenActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }

}
