package trickyquestion.messenger.popup_windows;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.util.preference.ThemePreference;

public class FriendRequestDialog {

    private final Context context;
    private String friendName;
    private String friendId;
    private int color;

    private DialogInterface.OnClickListener onPositiveButtonClickListener;
    private DialogInterface.OnClickListener onNegativeButtonClickListener;
    private AlertDialog alert;

    public FriendRequestDialog(final Context context, final String name, final String id) {
        this.context = context;
        this.friendName = name;
        this.friendId = id;
        this.color=  new ThemePreference(context).getPrimaryColor();
    }

    public void setOnPositiveButtonClickListener(DialogInterface.OnClickListener onPositiveButtonClickListener) {
        this.onPositiveButtonClickListener = onPositiveButtonClickListener;
    }

    public void setOnNegativeButtonClickListener(DialogInterface.OnClickListener onNegativeButtonClickListener) {
        this.onNegativeButtonClickListener = onNegativeButtonClickListener;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void show() {
        createAlert();
        setType();
        alert.show();
        customizeTheme();
    }

    private void createAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        alert = builder
                .setTitle(
                        getCustomizeTitle(String.format("Add user: %s? \nid: %s", friendName, friendId))
                )
                .setNegativeButton("no", (dialog, which) -> {
                    dialog.dismiss();
                    if (onNegativeButtonClickListener != null) onNegativeButtonClickListener.onClick(dialog, which);
                })
                .setPositiveButton("yes", (dialog, which) -> {
                    if (onPositiveButtonClickListener != null) onPositiveButtonClickListener.onClick(dialog, which);
                })
                .create();
    }

    private void setType() {
        alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    private void customizeTheme() {
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(color);
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(color);
    }

    private SpannableString getCustomizeTitle(@NotNull final String title) {
        final SpannableString str = new SpannableString(title);
        str.setSpan(new ForegroundColorSpan(color), 0, title.length(), Spannable.SPAN_USER);
        return str;
    }
}
