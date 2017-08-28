package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.MessageViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages.IMessageView;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Message;
import trickyquestion.messenger.R;

public class MessagePresenter implements IMessagePresenter {
    private final IMessageView view;
    private List<Message> messageList;

    public MessagePresenter(final IMessageView view) {
        this.view = view;
        messageList = Message.getMessages(40);
    }


    @Override
    public void onCreateView() {
        view.showMessageContent();
    }

    @Override
    public MessageViewHolder onCreateView(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messege, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final Message message = messageList.get(position);
        holder.message.setText(message.getMessageText());
    }

    @Override
    public int getCount() {
        return messageList.size();
    }
}
