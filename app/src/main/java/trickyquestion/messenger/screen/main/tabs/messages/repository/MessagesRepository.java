package trickyquestion.messenger.screen.main.tabs.messages.repository;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.screen.chat.model.ChatMessage;
import trickyquestion.messenger.screen.chat.repository.ChatMessageRepository;
import trickyquestion.messenger.screen.main.tabs.messages.model.Message;
import trickyquestion.messenger.util.java.string_helper.TimeFormatter;

public class MessagesRepository {

    private static List<Message> messages = new ArrayList<>();

    public static List<Message> getMessages()  {
        messages.clear();
        final List<ChatMessage> chatMessages = new ChatMessageRepository().getAllMessagesFromDB();
        for (int i = chatMessages.size() - 1; i >= 0; i--) {
            final ChatMessage item = chatMessages.get(i);
            final Message message = new Message(
                    item.getText(),
                    item.getNameFriend(),
                    item.getIdFriend(),
                    null,
                    TimeFormatter.convertTime(item.getTime(), "d MMM yyyy HH:mm:ss", "HH:mm:ss"),
                    true
            );
            if ( !existInList(messages, message.getIdFriend()) )
                messages.add(message);
        }
        return messages;
    }

    private static boolean existInList(List<Message> messages, String id) {
        for (Message message : messages) {
            if (message.getIdFriend().equals(id))
                return true;
        }
        return false;
    }
}
