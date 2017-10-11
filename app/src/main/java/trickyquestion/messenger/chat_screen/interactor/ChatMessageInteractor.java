package trickyquestion.messenger.chat_screen.interactor;


import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.repository.ChatMessageRepository;

public class ChatMessageInteractor {

    public static List<ChatMessage> getAllMessages() {
        return new ChatMessageRepository().getAllMessagesFromDB();
    }
    public static List<ChatMessage> getMessages(final String name) {
        return new ChatMessageRepository().getMessages(name);
    }
}
