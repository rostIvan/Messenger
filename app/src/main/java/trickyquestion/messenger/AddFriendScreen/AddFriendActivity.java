package trickyquestion.messenger.AddFriendScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import trickyquestion.messenger.R;

public class AddFriendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toast.makeText(this, "Add friend activity", Toast.LENGTH_SHORT).show();
    }
}
