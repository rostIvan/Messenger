package trickyquestion.messenger.login_screen.ask_password;

import android.content.Intent;
import android.support.v4.app.Fragment;

import trickyquestion.messenger.login_screen.SingleFragmentActivity;

public class AskPasswordActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new AskPasswordFragment();
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }

    private void closeApp() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
