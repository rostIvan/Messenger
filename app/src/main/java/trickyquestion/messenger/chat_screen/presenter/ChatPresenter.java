package trickyquestion.messenger.chat_screen.presenter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.adapters.ChatViewHolder;
import trickyquestion.messenger.chat_screen.interactor.ChatMessageInteractor;
import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.repository.ChatMessageRepository;
import trickyquestion.messenger.chat_screen.repository.IChatMessageRepository;
import trickyquestion.messenger.chat_screen.view.IChatView;
import trickyquestion.messenger.util.formatter.TimeFormatter;

public class ChatPresenter implements IChatPresenter {

    private final IChatView view;
    private final List<ChatMessage> chatMessages;
    private final IChatMessageRepository repository;

    public ChatPresenter(final IChatView view) {
        this.view = view;
//        this.chatMessages = ChatMessageInteractor.getAllMessagesFromDB();
        this.chatMessages = ChatMessageInteractor.getMessages(view.getFriendName());
        this.repository = new ChatMessageRepository();
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showMessages();
        view.setupListeners();
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }

    @Override
    public View.OnClickListener onSendButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view.getMessageText().isEmpty()) sendMessage(view.getMessageText());
                else view.showToast("Message is empty!");
            }
        };
    }

    @Override
    public void onClearMessagesItemCLick() {
        repository.deleteMessageTable(view.getFriendName());
        view.refreshRecycler();
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_chat_messege, parent, false
        );
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        final ChatMessage message = chatMessages.get(position);
        bindViewHolder(holder, message);
    }

    private void bindViewHolder(ChatViewHolder holder, ChatMessage message) {
        holder.textMessage.setText(message.getText());
        holder.timeMessage.setText(message.getTime());
        if (message.isMy()) setStyleMyMessage(holder.container, holder.textMessage, holder.timeMessage);
        else setStyleFriendMessage(holder.container, holder.textMessage, holder.timeMessage);
    }

    private void setStyleMyMessage(final View container, final TextView textMessage, final TextView timeMessage) {
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(130, 20, 30, 20);
        container.setLayoutParams(params);
        container.setBackgroundResource(R.drawable.shape_my_message);
        textMessage.setTextColor(Color.WHITE);
        timeMessage.setTextColor(container.getResources().getColor(R.color.colorTransparentGray));
    }
    private void setStyleFriendMessage(final View container, final TextView textMessage, final TextView timeMessage) {
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 20, 130, 20);
        container.setLayoutParams(params);
        container.setBackgroundResource(R.drawable.shape_friend_message);
        textMessage.setTextColor(Color.BLACK);
        timeMessage.setTextColor(Color.argb(100, 0, 0, 0));
    }

    private void addMessageToDb(final String  message) {
        final ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(message);
        chatMessage.setTime(TimeFormatter.getCurrentTime("d MMM yyyy HH:mm:ss"));
        chatMessage.setMeOwner(new Random().nextBoolean());
        chatMessage.setTable(view.getFriendName());
        repository.addMessage(chatMessage);
    }
    private void sendMessage(String message) {
        addMessageToDb(message);
        view.clearMessageText();
        view.refreshRecycler();
        view.scrollRecyclerToPosition(chatMessages.size() - 1);
    }
}
