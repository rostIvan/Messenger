package trickyquestion.messenger.chat_screen.interactor;


import java.util.List;

import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.chat_screen.repository.ChatMessageRepository;

public class ChatMessageInteractor {

    public static List<ChatMessage> getMessages() {
        return new ChatMessageRepository().getMessages();
    }
}
