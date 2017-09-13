package trickyquestion.messenger.main_screen.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.widget.RelativeLayout;

import trickyquestion.messenger.R;

public class PhotoDialog implements IDialogView {
    private Dialog dialog;
    private boolean isShow;

    public PhotoDialog (final Context context) {
        dialog = new Dialog(context);
        create();
    }

    @Override
    public void show() {
        dialog.show();
        isShow = true;
    }

    private PhotoDialog create() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_photo);
        dialog.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShow = false;
            }
        });
        return this;
    }

    public void setBackground(final Drawable drawable) {
        if (drawable != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(drawable);
    }

    public Window getWindow() {
        return dialog.getWindow();
    }

    @Override
    public boolean isShow() {
        return isShow;
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
