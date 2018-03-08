package trickyquestion.messenger.screen.tabs.messages.buisness;

import java.util.List;

import trickyquestion.messenger.screen.tabs.messages.data.Message;

public interface IMessagesInteractor {
    List<Message> getMessages();
    List<Message> getMessages(String query);
}
