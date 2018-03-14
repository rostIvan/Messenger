package trickyquestion.messenger.screen.main.tabs.messages.buisness;

import java.util.List;

import trickyquestion.messenger.screen.main.tabs.messages.data.Message;

public interface IMessagesInteractor {
    List<Message> getMessages();
    List<Message> getMessages(String query);
    void deleteEmptyTables();
}
