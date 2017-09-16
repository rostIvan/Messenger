package trickyquestion.messenger.login_screen;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;

public class LoginScreenActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return new LoginFragment();
    }
}
