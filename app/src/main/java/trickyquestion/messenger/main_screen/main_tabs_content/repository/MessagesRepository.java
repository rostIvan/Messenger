package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.repository.ChatMessageRepository;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

public class MessagesRepository {

    private static List<Message> messages = new ArrayList<>();

    public static List<Message> getMessages() {
        messages.clear();
        final List<ChatMessage> chatMessages = new ChatMessageRepository().getAllMessagesFromDB();
        for (int i = chatMessages.size() - 1; i >= 0; i--) {
            final Message message = new Message(
                    chatMessages.get(i).getText(),
                    chatMessages.get(i).getUserTableName(),
                    null,
                    chatMessages.get(i).getTime().substring(14),
                    true
            );
            if ( !existInList(messages, message.getNameSender()) )
                messages.add(message);
        }
        return messages;
    }

    private static boolean existInList(List<Message> messages, String name) {
        for (Message message : messages) {
            if (message.getNameSender().equals(name))
                return true;
        }
        return false;
    }
}
