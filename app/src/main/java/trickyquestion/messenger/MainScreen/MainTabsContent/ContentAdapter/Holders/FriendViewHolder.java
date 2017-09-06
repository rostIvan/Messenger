package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.PhotoDialog;
import trickyquestion.messenger.MainScreen.MainTabsContent.PhotoDialog.PhotoDialogView;
import trickyquestion.messenger.MainScreen.View.MainActivity;
import trickyquestion.messenger.R;
import trickyquestion.messenger.Util.Constants;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.friend_name)
    TextView name;
    public @BindView(R.id.friend_id)
    TextView id;
    public @BindView(R.id.friend_online_status)
    TextView onlineStatus;
    public @BindView(R.id.friend_photo)
    CircleImageView image;

    public FriendViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.friend_photo)
    void onPhotoClick() {

        final Activity activity = (Activity) itemView.getContext();
        Toast.makeText(activity, "Photo click", Toast.LENGTH_SHORT).show();

        final View screenView = activity.getWindow().getDecorView().getRootView();
        /* skip status bar in screenshot */
        int sizeBar = Constants.SIZE_NOTIFICATION_BAR;
        screenView.setDrawingCacheEnabled(true);

        final Bitmap bitmap = Bitmap.createBitmap(
                screenView.getDrawingCache(),
                0,
                sizeBar,
                screenView.getWidth(),
                screenView.getHeight() - sizeBar,
                null,
                true
        );
        screenView.setDrawingCacheEnabled(false);

        final BitmapDrawable screenShot = new BitmapDrawable(activity.getResources(), bitmap);
        final PhotoDialog photoDialog = new PhotoDialog(activity);
        photoDialog.setBackground(screenShot);
        photoDialog.show();

        // TODO: 06.09.17 Add blur with content
    }
}
