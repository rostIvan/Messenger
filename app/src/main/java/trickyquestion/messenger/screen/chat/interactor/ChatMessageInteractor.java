package trickyquestion.messenger.screen.chat.interactor;


import java.util.List;

import trickyquestion.messenger.screen.chat.model.ChatMessage;
import trickyquestion.messenger.screen.chat.repository.ChatMessageRepository;

public class ChatMessageInteractor {

    public static List<ChatMessage> getAllMessages() {
        return new ChatMessageRepository().getAllMessagesFromDB();
    }
    public static List<ChatMessage> getMessages(final String idFriend) {
        return new ChatMessageRepository().getMessages(idFriend);
    }
}
