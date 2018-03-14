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
    private FriendRepository friendRepository = FriendRepository.INSTANCE;
    private ChatMessageRepository chatRepository = ChatMessageRepository.INSTANCE;

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
            return date1.getYear() > date2.getYear() ? -1 :
                    date1.getMonth() > date2.getMonth() ? -1 :
                    date1.getDay() > date2.getDay() ? -1 :
                    date1.getHour() > date2.getHour() ? -1 :
                    date1.getMinute() > date2.getMinute() ? -1 :
                    date1.getSecond() > date2.getSecond() ? -1 :

                    date1.getYear() == date2.getYear() ? 0 :
                    date1.getMonth() == date2.getMonth() ? 0 :
                    date1.getDay() == date2.getDay() ? 0 :
                    date1.getHour() == date2.getHour() ? 0 :
                    date1.getMinute() == date2.getMinute() ? 0 :
                    date1.getSecond() == date2.getSecond() ? 0 : 1;
        });
        return messages;
    }
}
