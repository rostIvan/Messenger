package trickyquestion.messenger.MainScreen.MainTabsContent.Model;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String message;

    public Message() {
    }

    public Message(final String message) {
        this.message = message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessageText() {
        return message;
    }

    public static List<Message> getMessages(final int size) {
        final List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Message message = new Message("Some message");
            messageList.add(message);
        }
        return messageList;
    }
}
