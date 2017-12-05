package trickyquestion.messenger.util.temp_impl;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

public class MessageGetter {

    public static List<Message> getMessages(final int size) {
        final List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Message message = new Message(
                    "Some message: " + (i + 1), "Some name: " + (i + 1),
                    "Some id: " + (i + 1),
                    null, "12:00",
                    i % 2 != 0
            );
            messageList.add(message);
        }
        return messageList;
    }
}
