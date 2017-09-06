package trickyquestion.messenger.MainScreen.MainTabsContent.PhotoDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.widget.RelativeLayout;

import trickyquestion.messenger.R;

public class PhotoDialogView {
    private Dialog dialog;
    private boolean isShow;

    public PhotoDialogView(final Context context) {
        dialog = new Dialog(context);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
    }

    private void create() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_photo);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
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
