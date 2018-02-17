package trickyquestion.messenger.application.entry_point;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public final class EntryPoint extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();
    }

    private void startApp() {
        final AProgramStarter starter = new AppStarter(getApplicationContext());
        starter.start();
        this.finish();
    }
}
