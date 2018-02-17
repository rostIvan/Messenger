package trickyquestion.messenger.screen.chat.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.chat_message_container)
    public LinearLayout container;
    @BindView(R.id.chat_message_text)
    public TextView textMessage;
    @BindView(R.id.chat_message_time)
    public TextView timeMessage;
    public ChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
