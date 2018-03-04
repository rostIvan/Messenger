package trickyquestion.messenger.screen.tabs.friends.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {
    public @BindView(R.id.friend_name)
    TextView name;
    public @BindView(R.id.friend_online_status)
    TextView onlineStatus;
    public @BindView(R.id.friend_photo)
    CircleImageView image;

    public FriendViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
