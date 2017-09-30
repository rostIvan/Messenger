package trickyquestion.messenger.chat_screen.presenter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.chat_screen.adapters.ChatViewHolder;
import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.view.IChatView;
import trickyquestion.messenger.util.temp_impl.ChatMessageGetter;

public class ChatPresenter implements IChatPresenter {
    private final IChatView view;
    private final List<ChatMessage> chatMessages;

    public ChatPresenter(final IChatView view) {
        this.view = view;
        this.chatMessages = ChatMessageGetter.getMessages(40);
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showMessages();
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
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_add_friend, parent, false
        );
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        final ChatMessage message = chatMessages.get(position);
        bindViewHolder(holder, message);
    }

    private void bindViewHolder(ChatViewHolder holder, ChatMessage message) {

    }

}
