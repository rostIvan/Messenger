package trickyquestion.messenger.MainScreen.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.RelativeLayout;

import trickyquestion.messenger.R;

public class SettingMenuDialog {
    private Dialog dialog;
    private boolean isShow;

    public SettingMenuDialog(final Context context) {
        dialog = new Dialog(context);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
    }

    private void create() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        dialog.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShow = false;
            }
        });
    }

    public boolean isShow() {
        return isShow;
    }
}
