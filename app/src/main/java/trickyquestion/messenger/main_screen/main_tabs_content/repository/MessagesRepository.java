package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import java.util.List;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;
import trickyquestion.messenger.util.temp_impl.MessageGetter;

public class MessagesRepository {

    private static List<Message> messages;

    public static List<Message> getMessages() {
        messages = MessageGetter.getMessages(40);
        return messages;
    }

}
