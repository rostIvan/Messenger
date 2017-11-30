package trickyquestion.messenger.popup_windows;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.UUID;

import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.util.preference.ThemePreference;

public class FriendDialogRequest extends AppCompatActivity {

    private AlertDialog alertDialog;
    private String friendName;
    private String friendId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendName = getIntent().getStringExtra("friendName");
        friendId = getIntent().getStringExtra("friendId");

        showDialog();
        customizeTheme();
    }
    private void showDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Add to friend: " + getIntent().getStringExtra("friendName"))
                .setNegativeButton("no", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("yes", (dialog, which) -> {
                    addFriend();
                    finish();
                })
                .create();
        alertDialog.show();
    }

    private void addFriend() {
        final Friend f = new Friend();
        f.setName(friendName);
        f.setOnline(true);
        f.setId(UUID.fromString(friendId));
        FriendsRepository.addFriend(f);
    }

    private void customizeTheme() {
        final int color = new ThemePreference(this).getPrimaryColor();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(color);
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(color);
    }
}
