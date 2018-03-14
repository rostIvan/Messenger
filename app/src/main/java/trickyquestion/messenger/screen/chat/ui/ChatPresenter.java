package trickyquestion.messenger.screen.chat.ui;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.chat.buisness.ChatInteractor;
import trickyquestion.messenger.screen.chat.buisness.EventManager;
import trickyquestion.messenger.screen.chat.buisness.IChatInteractor;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.activity.MvpPresenter;

public class ChatPresenter extends MvpPresenter<ChatActivity, BaseRouter> implements IChatPresenter {
    private final IChatView view = getView();
    private final EventManager eventManager = new EventManager(this);
    private final IChatInteractor interactor = new ChatInteractor();

    public ChatPresenter(@NotNull ChatActivity view, @NotNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) { eventManager.subscribe(); }

    @Override
    public void onStart() {
        checkTalkPossibility();
        updateMessages();
    }

    @Override
    public void onDestroy() { eventManager.unsubscribe();}

    @Override
    public void connectToFriend(Friend friend) {
        interactor.connect(friend);
    }

    @Override
    public void onSendMessageClick(String message) {
        if (message.isEmpty()) {
            view.showToast("Message is empty!");
            return;
        }
        interactor.saveMessage(message);
        interactor.sendMessage(message);
        view.clearInputText();
    }

    @Override
    public void onClearMessagesItemCLick() {
        interactor.clearChatMessages();
    }

    @Override
    public void onReceiveMessage(String message, IFriend from) {
        interactor.receiveMessage(message, from);
    }

    @Override
    public void onChangeNetwork(NetworkState newNetworkState) {
        switch (newNetworkState) {
            case ACTIVE: view.showSendButton(); break;
            case INACTIVE: view.hideSendButton(); break;
        }
    }

    @Override
    public void updateMessages() {
        view.onUiThread(this::showChatMessages);
        view.updateMessages();
    }

    @Override
    public void onFriendChange() {
        checkTalkPossibility();
    }

    private void checkTalkPossibility() {
        switch (Network.GetCurrentNetworkState()) {
            case ACTIVE: view.showSendButton(); break;
            case INACTIVE: view.hideSendButton(); break;
        }
        final Friend friend = interactor.getFriend();
        if (!friend.isOnline()) view.hideSendButton();
        else view.showSendButton();
    }

    private void showChatMessages() {
        final List<ChatMessage> chatMessages = interactor.getChatMessages();
        view.showChatMessages(chatMessages);
    }
}
