package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.MainScreen.View.FriendProfileView;
import trickyquestion.messenger.MainScreen.View.Dialogs.PhotoDialog;
import trickyquestion.messenger.R;
import trickyquestion.messenger.Util.Constants;

//TODO:clean imports

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

}
