package trickyquestion.messenger.screen.tabs.messages.buisness;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.tabs.messages.data.Message;
import trickyquestion.messenger.util.java.maping.MessageFilter;

public class MessagesInteractor implements IMessagesInteractor {

    @Override
    public List<Message> getMessages() {
        return Arrays.asList(
                new Message("Hello", new Friend("Vasya", UUID.randomUUID(), Friend.Status.OFFLINE), Calendar.getInstance()),
                new Message("Hi", new Friend("Petya", UUID.randomUUID(), Friend.Status.OFFLINE), Calendar.getInstance()),
                new Message("How are you?", new Friend("Kolya", UUID.randomUUID(), Friend.Status.OFFLINE), Calendar.getInstance()),
                new Message("What's up?", new Friend("Teodor", UUID.randomUUID(), Friend.Status.OFFLINE), Calendar.getInstance())
        );
    }

    @Override
    public List<Message> getMessages(String query) {
        final List<Message> messages = getMessages();
        return MessageFilter.filter(messages, query);
    }
}
