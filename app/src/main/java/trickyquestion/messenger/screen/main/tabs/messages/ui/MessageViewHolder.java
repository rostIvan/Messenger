package trickyquestion.messenger.screen.main.tabs.messages.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.message_friend_text)
    TextView message;
    public @BindView(R.id.message_friend_name)
    TextView name;
    public @BindView(R.id.message_friend_time)
    TextView time;
    public @BindView(R.id.message_friend_photo)
    CircleImageView image;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
