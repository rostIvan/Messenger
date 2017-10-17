package trickyquestion.messenger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import trickyquestion.messenger.util.starter.AppStarter;
import trickyquestion.messenger.util.starter.IStarter;

public class StartController extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();
    }

    private void startApp() {
        final IStarter starter = new AppStarter(getApplicationContext());
        starter.start();
        this.finish();
    }
}
