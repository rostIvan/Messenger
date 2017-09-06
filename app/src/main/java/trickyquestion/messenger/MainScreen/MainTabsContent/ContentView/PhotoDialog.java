package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.RelativeLayout;

import trickyquestion.messenger.R;

public class PhotoDialog  {
    private Dialog dialog;
    private boolean isShow;

    public PhotoDialog (final Context context) {
        dialog = new Dialog(context);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
    }

    private void create() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_photo);
        dialog.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShow = false;
            }
        });
    }

    public void setBackground(final Drawable drawable) {
        if (drawable != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(drawable);
    }

    public boolean isShow() {
        return isShow;
    }
}
