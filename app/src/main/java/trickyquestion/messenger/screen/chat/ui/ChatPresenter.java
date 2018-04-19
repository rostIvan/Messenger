package trickyquestion.messenger.screen.chat.ui;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.chat.buisness.ChatEventManager;
import trickyquestion.messenger.screen.chat.buisness.IChatInteractor;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.activity.MvpPresenter;

public class ChatPresenter extends MvpPresenter<IChatView, BaseRouter> implements IChatPresenter {
    private final IChatInteractor interactor;
    private ChatEventManager eventManager;

    public ChatPresenter(@NotNull IChatView view,
                         @NotNull BaseRouter router,
                         @NotNull IChatInteractor chatInteractor) {
        super(view, router);
        this.interactor = chatInteractor;
    }

    public void attach(ChatEventManager eventManager) {
        this.eventManager = eventManager;
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
            getView().showToast("Message is empty!");
            return;
        }
        interactor.saveMessage(message);
        interactor.sendMessage(message);
        getView().clearInputText();
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
            case ACTIVE: getView().showSendButton(); break;
            case INACTIVE: getView().hideSendButton(); break;
        }
    }

    @Override
    public void updateMessages() {
        getView().onUiThread(this::showChatMessages);
        getView().updateMessages();
    }

    @Override
    public void onFriendChange() {
        checkTalkPossibility();
    }

    private void checkTalkPossibility() {
        switch (Network.GetCurrentNetworkState()) {
            case ACTIVE: getView().showSendButton(); break;
            case INACTIVE: getView().hideSendButton(); break;
        }
        final Friend friend = interactor.getFriend();
        if (!friend.isOnline()) getView().hideSendButton();
        else getView().showSendButton();
    }

    private void showChatMessages() {
        final List<ChatMessage> chatMessages = interactor.getChatMessages();
        getView().showChatMessages(chatMessages);
    }
}
