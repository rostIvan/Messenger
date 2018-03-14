package trickyquestion.messenger.kotlin.data.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import trickyquestion.messenger.data.repository.ChatMessageRepository;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ChatMessageRepositoryTest {

    private final Context context = InstrumentationRegistry.getContext();
    private final ChatMessageRepository repository = ChatMessageRepository.INSTANCE;

    @Before
    public void before() {
        Realm.init(context);
        repository.deleteAll();
    }

    @After
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void saveChatMessage_isCorrect() {
        final ChatMessage message = new ChatMessage("Hello", UUID.randomUUID(), ChatMessage.Sender.FRIEND, new MessageDate());
        repository.save(message);
        assertFalse(repository.isEmpty());
        assertEquals(repository.count(), 1);
        final ChatMessage chatMessage = repository.first();
        assertEquals(chatMessage.getText(), "Hello");
        assertEquals(chatMessage.isMine(), false);
    }


    @Test
    public void deleteChatMessage_isCorrect() {
        final ChatMessage message1 = new ChatMessage("Hello",UUID.randomUUID(), ChatMessage.Sender.I, new MessageDate());
        final ChatMessage message2 = new ChatMessage("Hi", UUID.randomUUID(), ChatMessage.Sender.FRIEND, new MessageDate());
        repository.save(message1);
        repository.save(message2);
        assertEquals(repository.count(), 2);
        repository.delete(message1);
        assertEquals(repository.count(), 1);
        repository.delete(message2);
        assertTrue(repository.isEmpty());
    }

    @Test
    public void saveAllChatMessage_isCorrect() {
        final List<ChatMessage> messages = Arrays.asList(
                new ChatMessage("Hello", UUID.randomUUID(), true, new MessageDate()),
                new ChatMessage("Hi", UUID.randomUUID(), true, new MessageDate()),
                new ChatMessage("What's up", UUID.randomUUID(), true, new MessageDate()));
        repository.saveAll(messages);
        assertEquals(repository.count(), 3);
        final List<ChatMessage> fromRepository = repository.findAll();
        for (int i = 0; i < messages.size(); i++) {
            assertEquals(fromRepository.get(i).getText(), messages.get(i).getText());
        }
    }

    @Test
    public void deleteAllChatMessage_isCorrect() {
        final ChatMessage message1 = new ChatMessage("Hello", UUID.randomUUID(), true, new MessageDate());
        final ChatMessage message2 = new ChatMessage("How are you?", UUID.randomUUID(), false, new MessageDate());
        repository.saveAll( Arrays.asList(message1, message2) );
        assertTrue( repository.count() == 2 );
        repository.deleteAll( Arrays.asList(message1, message2) );
        assertTrue( repository.count() == 0 );
    }

    @Test
    public void firstAndLast_shouldReturnActualValue() {
        final ChatMessage message1 = new ChatMessage("Hello", UUID.randomUUID(), true, MessageDate.getCurrent());
        final ChatMessage message2 = new ChatMessage("How are you?", UUID.randomUUID(), false, MessageDate.getCurrent());
        final ChatMessage message3 = new ChatMessage("Hi", UUID.randomUUID(), false, MessageDate.getCurrent());
        repository.saveAll( Arrays.asList(message1, message2, message3) );
        final ChatMessage first = repository.first();
        final ChatMessage last = repository.last();
        assertEquals(first.getText(), message1.getText());
        assertEquals(last.getText(), message3.getText());
    }

    @Test
    public void deleteMessagesByFriendId_isCorrect() {
        final UUID vasyaId = UUID.randomUUID();
        final UUID petyaId = UUID.randomUUID();
        final ChatMessage message1 = new ChatMessage("Hi", vasyaId, ChatMessage.Sender.FRIEND, MessageDate.getCurrent());
        final ChatMessage message2 = new ChatMessage("Hello", vasyaId, ChatMessage.Sender.I, MessageDate.getCurrent());
        final ChatMessage message3 = new ChatMessage("How are you doing?", vasyaId, ChatMessage.Sender.I, MessageDate.getCurrent());
        final ChatMessage message4 = new ChatMessage("What's up?", petyaId, ChatMessage.Sender.FRIEND, MessageDate.getCurrent());
        repository.saveAll( Arrays.asList(message1, message2, message3, message4) );
        assertEquals(repository.count(), 4);
        repository.deleteFriendMessages(vasyaId);
        assertEquals(repository.count(), 1);
        for (ChatMessage message : repository.findAll()) {
            assertNotSame(message.getFriendId(), vasyaId);
        }
    }

    @Test
    public void findLastFriendMessage_shouldReturnActualValue() {
        final UUID vasyaId = UUID.randomUUID();
        final UUID petyaId = UUID.randomUUID();
        final ChatMessage message1 = new ChatMessage("Hi", vasyaId, ChatMessage.Sender.FRIEND, MessageDate.getCurrent());
        final ChatMessage message2 = new ChatMessage("Hello", vasyaId, ChatMessage.Sender.I, MessageDate.getCurrent());
        final ChatMessage message3 = new ChatMessage("How are you doing?", vasyaId, ChatMessage.Sender.I, MessageDate.getCurrent());
        final ChatMessage message4 = new ChatMessage("What's up?", petyaId, ChatMessage.Sender.FRIEND, MessageDate.getCurrent());
        final ChatMessage message5 = new ChatMessage("What you been up to?", petyaId, ChatMessage.Sender.FRIEND, MessageDate.getCurrent());
        repository.saveAll( Arrays.asList(message1, message2, message3, message4, message5) );
        final ChatMessage lastVasyaMessage = repository.findLastFriendMessage(vasyaId);
        final ChatMessage lastPetyaMessage = repository.findLastFriendMessage(petyaId);
        if (lastPetyaMessage == null || lastVasyaMessage == null)
            throw new AssertionError("Object from repository are null!");
        assertEquals(lastVasyaMessage.getText(), "How are you doing?");
        assertEquals(lastPetyaMessage.getText(), "What you been up to?");
    }
}
