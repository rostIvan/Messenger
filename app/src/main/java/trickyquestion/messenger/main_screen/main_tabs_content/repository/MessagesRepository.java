package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import java.util.List;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

public class MessagesRepository {

    private static List<Message> messages;

    public static List<Message> getFriends() {
        messages = Message.getMessages(40);
        return messages;
    }

}
