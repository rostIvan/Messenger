package trickyquestion.messenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import trickyquestion.messenger.Util.MainUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MainUtil.MAIN_LAYOUT);
    }

}
