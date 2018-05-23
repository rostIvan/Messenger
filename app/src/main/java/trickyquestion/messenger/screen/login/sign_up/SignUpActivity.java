package trickyquestion.messenger.screen.login.sign_up;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.screen.login.SingleFragmentActivity;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class SignUpActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        final SignUpFragment signUpFragment = new SignUpFragment();
        final SignUpViewModel viewModel = new SignUpViewModel(
                ApplicationRouter.from(this), new AuthPreference(this), new P2PConnector(this));
        signUpFragment.attach(viewModel);
        return signUpFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
