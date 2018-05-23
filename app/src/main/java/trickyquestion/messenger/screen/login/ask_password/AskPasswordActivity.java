package trickyquestion.messenger.screen.login.ask_password;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import trickyquestion.messenger.screen.login.SingleFragmentActivity;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public class AskPasswordActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        final AskPasswordFragment askPasswordFragment = new AskPasswordFragment();
        final AskPasswordViewModel viewModel = new AskPasswordViewModel(
                ApplicationRouter.from(this), new AuthPreference(this));
        askPasswordFragment.attach(viewModel);
        return askPasswordFragment;
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
