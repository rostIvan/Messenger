package trickyquestion.messenger.MainScreen.View.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import trickyquestion.messenger.R;
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
        // FIXME: 09.09.2017 Crash after change orientation
        //     photoDialog.setBackground(getScreenShoot());
    }

    public void show() {
        photoDialog.show();
    }

    public boolean isShow() {
        return photoDialog.isShow();
    }

    private Drawable getScreenShoot() {
        final Activity activity = (Activity) context;
        final View screenView = activity.getWindow().getDecorView().getRootView();
        /* skip status bar in screenshot */
        int sizeBar = Constants.SIZE_NOTIFICATION_BAR;
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        final Bitmap bitmap = Bitmap.createBitmap(
                screenView.getDrawingCache(),
                0, sizeBar, // x, y
                screenView.getWidth(), screenView.getHeight() - sizeBar, // width, height
                null, true
        );
        screenView.setDrawingCacheEnabled(false);
        return new BitmapDrawable(activity.getResources(), bitmap);
    }
}
