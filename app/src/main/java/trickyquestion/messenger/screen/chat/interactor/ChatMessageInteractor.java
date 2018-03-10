package trickyquestion.messenger.screen.chat.interactor;


import java.util.List;

import trickyquestion.messenger.screen.chat.model.ChatMessageM;
import trickyquestion.messenger.screen.chat.repository.ChatMessageRepository;

public class ChatMessageInteractor {

    public static List<ChatMessageM> getAllMessages() {
        return new ChatMessageRepository().getAllMessagesFromDB();
    }
    public static List<ChatMessageM> getMessages(final String idFriend) {
        return new ChatMessageRepository().getMessages(idFriend);
    }
}
