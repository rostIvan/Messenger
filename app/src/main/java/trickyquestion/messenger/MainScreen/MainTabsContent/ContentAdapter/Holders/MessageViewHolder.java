package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages.IMessageView;
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
