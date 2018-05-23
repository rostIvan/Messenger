package trickyquestion.messenger.screen.main.tabs.messages.buisness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.data.repository.ChatMessageRepository;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.util.java.maping.MessageFilter;

public class MessagesInteractor implements IMessagesInteractor {
    private final FriendRepository friendRepository;
    private final ChatMessageRepository chatRepository;

    public MessagesInteractor(FriendRepository friendRepository, ChatMessageRepository chatRepository) {
        this.friendRepository = friendRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Message> getMessages() {
        final List<Message> messages = new ArrayList<>();
        for (Friend friend : friendRepository.findAll()) {
            final ChatMessage lastMessage = chatRepository.findLastFriendMessage(friend.getId());
            if (lastMessage == null) continue;
            final Message message = new Message(lastMessage.getText(), friend, lastMessage.getDate());
            messages.add(message);
        }
        return getSortedByDate(messages);
    }

    @Override
    public List<Message> getMessages(String query) {
        final List<Message> messages = getMessages();
        return MessageFilter.filter(messages, query);
    }

    @Override
    public void deleteEmptyTables() {
        final List<Friend> friends = friendRepository.findAll();
        final List<ChatMessage> chatMessages = chatRepository.findAll();
        for (ChatMessage message : chatMessages) {
            final UUID friendId = message.getFriendId();
            final Friend friend = friendRepository.findById(friendId);
            if (!friends.contains(friend))
                chatRepository.delete(message);
        }
    }

    private List<Message> getSortedByDate(List<Message> messages) {
        Collections.sort(messages, (t1, t2) -> {
            final MessageDate date1 = t1.getDate();
            final MessageDate date2 = t2.getDate();

            if(date1.getYear() > date2.getYear())
                return -1;
            else
                if(date1.getYear()< date2.getYear())
                    return 1;
            else
            if(date1.getMonth() > date2.getMonth())
                return -1;
            else
                if(date1.getMonth() < date2.getMonth())
                    return 1;
            else
            if(date1.getDay() > date2.getDay())
                return -1;
            else
                if(date1.getDay() < date2.getDay())
                    return 1;
            else
            if(date1.getHour() > date2.getHour())
                return -1;
            else
                if(date1.getHour() < date2.getHour())
                    return 1;
            else
            if(date1.getMinute() > date2.getMinute())
                return -1;
            else
                if(date1.getMinute() < date2.getMinute())
                    return 1;
            else
            if(date1.getSecond() > date2.getSecond())
                return -1;
            else
                if(date1.getSecond() < date2.getSecond())
                    return 1;
            else
                return 0;
        });
        return messages;
    }
}
