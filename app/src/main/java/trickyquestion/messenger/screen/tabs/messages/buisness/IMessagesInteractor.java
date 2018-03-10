package trickyquestion.messenger.screen.tabs.messages.buisness;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.screen.tabs.messages.data.Message;

public interface IMessagesInteractor {
    List<Message> getMessages();
    List<Message> getMessages(String query);
    void deleteEmptyTables();
}
