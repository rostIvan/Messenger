package trickyquestion.messenger.screen.chat.presenter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.R;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.events.EReceivedMsg;
import trickyquestion.messenger.screen.chat.model.ChatMessage;
import trickyquestion.messenger.screen.chat.repository.ChatMessageRepository;
import trickyquestion.messenger.screen.chat.repository.IChatMessageRepository;
import trickyquestion.messenger.screen.chat.view.IChatView;
import trickyquestion.messenger.screen.chat.view.adapter.ChatViewHolder;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.repository.FriendsRepository;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDataBaseEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.java.string_helper.TimeFormatter;

public class ChatPresenter implements IChatPresenter {

    private final IChatView view;
    private final List<ChatMessage> chatMessages;
    private final IChatMessageRepository repository;

    public ChatPresenter(final IChatView view) {
        this.view = view;
        this.repository = new ChatMessageRepository();
        this.chatMessages = repository.getMessages(view.getFriendId());
    }

    @Override
    public void onCreate() {
        view.customizeTheme();
        view.customizeToolbar();
        view.showMessages();
        view.setupListeners();
        view.scrollRecyclerToPosition(chatMessages.size() - 1);
        checkSendingPossibility();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return v -> view.goBack();
    }

    @Override
    public View.OnClickListener onSendButtonClick() {
        return v -> {
            if (!view.getMessageText().isEmpty()) {
                sendMessage(view.getMessageText());
                view.clearMessageText();
            }
            else view.showToast("Message is empty!");
        };
    }

    @Override
    public void onClearMessagesItemCLick() {
        repository.deleteMessages(view.getFriendId());
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
        if (message.isMy())
            view.setStyleForMyMessage(holder.container, holder.textMessage, holder.timeMessage);
        else
            view.setStyleForFriendMessage(holder.container, holder.textMessage, holder.timeMessage);
    }

    private void checkSendingPossibility() {
        boolean serviceConnected = P2PProtocolConnector.isServiceConnected();
        boolean networkIsActive = Network.GetCurrentNetworkState() == NetworkState.ACTIVE;
        boolean isFriendOnline = FriendsRepository.getFriend(view.getFriendId()).isOnline();
        if (serviceConnected && networkIsActive && isFriendOnline) {
            view.showFields();
        }
        else {
            view.hideFields();
        }
    }

    private void addMyMessageToDb(final String message) {
        final ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(message);
        chatMessage.setTime(TimeFormatter.getCurrentTime("d MMM yyyy HH:mm:ss"));
        chatMessage.setMeOwner(true);
        chatMessage.setNameFriend(view.getFriendName());
        chatMessage.setIdFriend(view.getFriendId());
        repository.addMessage(chatMessage);
    }

    private void addReceiveMessageToDb(final String message, final UUID id, final String name) {
        final ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(message);
        chatMessage.setTime(TimeFormatter.getCurrentTime("d MMM yyyy HH:mm:ss"));
        chatMessage.setMeOwner(false);
        chatMessage.setNameFriend(name);
        chatMessage.setIdFriend(id.toString());
        repository.addMessage(chatMessage);
    }

    private void sendMessage(final String message) {
        addMyMessageToDb(message);
        updateMessages();
        P2PProtocolConnector.ProtocolInterface().SendMsg(UUID.fromString(view.getFriendId()), message);
    }

    private void receiveMessage(final String message, final UUID fromId, final String fromFriend) {
        addReceiveMessageToDb(message, fromId, fromFriend);
        updateMessages();
    }

    private void updateMessages() {
        view.runOnUIThread(() -> {
            view.refreshRecycler();
            view.scrollRecyclerToPosition(chatMessages.size() - 1);
        });
    }

    public void onEvent(ENetworkStateChanged event) {
        switch (event.getNewNetworkState()) {
            case ACTIVE: view.showFields(); break;
            case INACTIVE: view.hideFields(); break;
        }
    }

    public void onEvent(ChangeFriendDataBaseEvent event) {
        final Friend friend = FriendsRepository.getFriend(view.getFriendId());
        if (friend.isOnline()) view.showFields();
        else view.hideFields();
    }

    public void onEvent(ChangeThemeEvent event) {
        view.customizeTheme();
    }

    public void onEvent(EReceivedMsg event) {
        receiveMessage(event.getMsg(), event.getFrom().getID(), event.getFrom().getName());
    }
}
