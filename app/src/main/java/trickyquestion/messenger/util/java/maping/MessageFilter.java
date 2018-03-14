package trickyquestion.messenger.util.java.maping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import trickyquestion.messenger.screen.main.tabs.messages.data.Message;

public class MessageFilter {
    public static List<Message> filter(final List<Message> messages, final String searchValue) {
        if (searchValue.isEmpty() || searchValue.length() == 0) return messages;
        final List<Message> result = new ArrayList<>();
        for (Message message : messages) {
            final String name = message.getFriend().getName().toLowerCase(Locale.getDefault());
            final String query = searchValue.toLowerCase(Locale.getDefault());
            if (name.contains(query))
                result.add(message);
        }
        return result;
    }
}
