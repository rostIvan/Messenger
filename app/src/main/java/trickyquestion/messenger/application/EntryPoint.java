package trickyquestion.messenger.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

public final class EntryPoint extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();
    }

    private void startApp() {
        final ApplicationStarter application = new ApplicationStarter(
                new AuthPreference(this),
                ApplicationRouter.from(this),
                new P2PConnector(this));
        application.start();
        this.finish();
    }
}
