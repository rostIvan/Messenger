package trickyquestion.messenger.settings_screen.view.dialogs;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;

public interface IChangeDialog {
    String getEnteredText();
    void setOnPositiveClickListener(DialogInterface.OnClickListener listener);
    void setOnNegativeClickListener(DialogInterface.OnClickListener listener);
    void show(FragmentManager fn, String tag);
    void dismiss();
}
