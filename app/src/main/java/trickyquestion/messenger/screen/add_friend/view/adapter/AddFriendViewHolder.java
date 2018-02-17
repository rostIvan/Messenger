package trickyquestion.messenger.screen.add_friend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.R;

public class AddFriendViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.add_friend_name)
    public TextView name;
    @BindView(R.id.add_friend_id)
    public TextView id;
    @BindView(R.id.add_friend_photo)
    public CircleImageView image;
    @BindView(R.id.button_add_friend)
    public Button buttonAddFriend;

    public AddFriendViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
