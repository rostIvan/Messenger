package trickyquestion.messenger.util.temp_impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;

public class ChatMessageGetter {
    public static List<ChatMessage> getMessages(int size) {
        final List<ChatMessage> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final ChatMessage message = new ChatMessage();
            message.setText("Text: " + i);
            message.setTime(Calendar.getInstance().getTime().toString());
            message.setMeOwner(i % 2 != 0);

            list.add(message);
        }
        return list;
    }
}
