package trickyquestion.messenger.screen.tabs.chat.buisness;

import java.util.List;

import trickyquestion.messenger.data.repository.ChatMessageRepository;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.tabs.chat.data.ChatMessage;
import trickyquestion.messenger.screen.tabs.chat.data.MessageDate;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;

public class ChatInteractor implements IChatInteractor {
    private final ChatMessageRepository messageRepository = ChatMessageRepository.INSTANCE;
    private final FriendRepository friendRepository = FriendRepository.INSTANCE;
    private Friend friend;

    @Override
    public void connect(Friend friend) {
        this.friend = friend;
    }

    @Override
    public Friend getFriend() {
        return friendRepository.findById(friend.getId());
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return messageRepository.findFriendMessages(friend.getId());
    }

    @Override
    public void sendMessage(String message) {
        P2PProtocolConnector.ProtocolInterface().SendMsg(friend.getId(), message);
    }

    @Override
    public void saveMessage(String message) {
        final ChatMessage chatMessage = new ChatMessage(message);
        chatMessage.setDate(MessageDate.getCurrent());
        chatMessage.setFriendId(friend.getId());
        chatMessage.setMine(true);
        messageRepository.save(chatMessage);
    }

    @Override
    public void receiveMessage(String message, IFriend from) {
        final ChatMessage chatMessage = new ChatMessage(message);
        chatMessage.setDate(MessageDate.getCurrent());
        chatMessage.setFriendId(friend.getId());
        chatMessage.setMine(false);
        messageRepository.save(chatMessage);
    }

    @Override
    public void clearChatMessages() {
        messageRepository.deleteFriendMessages(friend.getId());
    }
}
