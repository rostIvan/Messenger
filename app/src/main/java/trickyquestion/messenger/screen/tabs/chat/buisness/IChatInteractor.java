package trickyquestion.messenger.screen.tabs.chat.buisness;


import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.tabs.chat.data.ChatMessage;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;

public interface IChatInteractor {
    void connect(Friend friend);
    List<ChatMessage> getChatMessages();
    Friend getFriend();
    void sendMessage(String message);
    void saveMessage(String message);
    void receiveMessage(String message, IFriend from);
    void clearChatMessages();
}
