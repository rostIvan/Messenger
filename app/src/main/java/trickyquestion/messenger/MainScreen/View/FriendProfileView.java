package trickyquestion.messenger.MainScreen.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewTreeObserver;

import trickyquestion.messenger.MainScreen.View.Dialogs.PhotoDialog;
import trickyquestion.messenger.Util.Constants;

public class FriendProfileView {
    private final Context context;
    private PhotoDialog photoDialog;

    public FriendProfileView(final Context context) {
        this.context = context;
        create();
    }

    private void create() {
        photoDialog = new PhotoDialog(context);
        photoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void show() {
        photoDialog.show();
    }

    public boolean isShowing() {
        return photoDialog.isShow();
    }

    public void dismiss() {
        photoDialog.dismiss();
    }
}
