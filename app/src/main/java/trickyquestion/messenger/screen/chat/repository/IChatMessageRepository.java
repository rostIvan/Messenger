package trickyquestion.messenger.screen.chat.repository;

import java.util.List;

import trickyquestion.messenger.screen.chat.model.ChatMessageM;

public interface IChatMessageRepository {
    void addMessage(final ChatMessageM message);
    void deleteMessage(final ChatMessageM message);
    void deleteMessages(final String id);
    void deleteAllMessages();
    List<ChatMessageM> getAllMessagesFromDB();
    List<ChatMessageM> getMessages(final String id);
}
