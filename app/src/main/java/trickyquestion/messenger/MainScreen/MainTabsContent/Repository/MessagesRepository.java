package trickyquestion.messenger.MainScreen.MainTabsContent.Repository;

import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Message;
//TODO: create controles and delete this imitation
public class MessagesRepository {

    private static List<Message> messages;

    public static List<Message> getFriends() {
        messages = Message.getMessages(40);
        return messages;
    }

}
