package trickyquestion.messenger.util.temp_impl;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.util.formatter.TimeFormatter;

public class ChatMessageGetter {
    public static List<ChatMessage> getMessages(int size) {
        final List<ChatMessage> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final ChatMessage message = new ChatMessage();
            message.setText("Text: " + i);
            message.setTime(TimeFormatter.getCurrentTime("d MMM yyyy HH:mm:ss"));
            message.setMeOwner(i % 2 != 0);
            list.add(message);
        }
        return list;
    }
}
