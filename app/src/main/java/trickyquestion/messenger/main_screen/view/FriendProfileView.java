package trickyquestion.messenger.main_screen.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import trickyquestion.messenger.main_screen.view.dialogs.PhotoDialog;

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
