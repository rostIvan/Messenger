package trickyquestion.messenger.chat_screen.repository;

import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;

public interface IChatMessageRepository {
    void addMessage(final ChatMessage message);
    void deleteMessage(final ChatMessage message);
    void deleteAllMessages();
    List<ChatMessage> getMessages();
}
