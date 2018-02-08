package trickyquestion.messenger.screen.chat.repository;

import java.util.List;

import trickyquestion.messenger.screen.chat.model.ChatMessage;

public interface IChatMessageRepository {
    void addMessage(final ChatMessage message);
    void deleteMessage(final ChatMessage message);
    void deleteMessages(final String id);
    void deleteAllMessages();
    List<ChatMessage> getAllMessagesFromDB();
    List<ChatMessage> getMessages(final String id);
}
