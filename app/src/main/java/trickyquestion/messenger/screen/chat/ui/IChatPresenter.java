package trickyquestion.messenger.screen.chat.ui;

import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public interface IChatPresenter {
    void connectToFriend(Friend friend);
    void onSendMessageClick(String message);
    void onClearMessagesItemCLick();
    void onReceiveMessage(String message, IFriend from);
    void onChangeNetwork(NetworkState newNetworkState);
    void updateMessages();
    void onFriendChange();
}
