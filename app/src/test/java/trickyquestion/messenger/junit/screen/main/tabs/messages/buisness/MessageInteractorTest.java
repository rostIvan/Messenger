package trickyquestion.messenger.junit.screen.main.tabs.messages.buisness;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.data.repository.ChatMessageRepository;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesInteractor;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MessageInteractorTest {
    @Mock FriendRepository friendRepository;
    @Mock ChatMessageRepository messageRepository;
    IMessagesInteractor interactor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        interactor = new MessagesInteractor(friendRepository, messageRepository);
    }

    @Test
    public void deleteEmptyTables_shouldClearMessagesWithoutFriend() {
        final List<Friend> friends = Arrays.asList(
                new Friend("Alex", UUID.randomUUID()),
                new Friend("John", UUID.randomUUID()), new Friend("Ilon", UUID.randomUUID()));
        final Friend friend = friends.get(0);
        final ChatMessage friendMessage = new ChatMessage("hi", friend.getId(), false, null);
        final ChatMessage messageWithoutFriend =
                new ChatMessage("Hello", UUID.randomUUID(), false, null);
        final List<ChatMessage> chatMessages = Arrays.asList(messageWithoutFriend, friendMessage);

        when(friendRepository.findAll()).thenReturn(friends);
        when(friendRepository.findById(friend.getId())).thenReturn(getById(friends, friend.getId()));
        doReturn(chatMessages).when(messageRepository).findAll();

        interactor.deleteEmptyTables();
        verify(messageRepository).delete(messageWithoutFriend);
    }

    @Test
    public void getMessages_shouldReturnLastFriendMessageByEveryUser() {
        final Friend jack = new Friend("Jack", UUID.randomUUID());
        final Friend john = new Friend("John", UUID.randomUUID());
        final Friend jozef = new Friend("Jozef", UUID.randomUUID());
        final List<Friend> friends = Arrays.asList(jack, john, jozef);
        final List<ChatMessage> chatMessages = Arrays.asList(
                new ChatMessage("hi", jack.getId(), false, new MessageDate(2018, 1, 1, 3, 20, 20)),
                new ChatMessage("hello", jack.getId(), false, new MessageDate(2018, 1, 1, 3, 21, 20)),
                new ChatMessage("how are you?", john.getId(), false, new MessageDate(2018, 1, 2, 3, 20, 20)),
                new ChatMessage("ok", jack.getId(), false, new MessageDate(2018, 1, 1, 4, 20, 20))
        );

        when(messageRepository.findAll()).thenReturn(chatMessages);
        friends.forEach(f -> doReturn(lastFriendMessage(chatMessages, f.getId()))
                .when(messageRepository).findLastFriendMessage(f.getId()));
        when(friendRepository.findAll()).thenReturn(friends);


        final List<Message> messages = interactor.getMessages();
        assertEquals(messages.size(), 2);
        final Message lastJohnMessage = messages.get(0);
        final Message lastJackMessage = messages.get(1);
        assertEquals(lastJackMessage.getText(), "ok");
        assertEquals(lastJohnMessage.getText(), "how are you?");
    }

    @Test
    public void getMessagesByQuery_shouldReturnLastMessageByQuery() {
        final Friend jack = new Friend("Jack", UUID.randomUUID());
        final Friend john = new Friend("John", UUID.randomUUID());
        final Friend jozef = new Friend("Jozef", UUID.randomUUID());
        final List<Friend> friends = Arrays.asList(jack, john, jozef);
        final List<ChatMessage> chatMessages = Arrays.asList(
                new ChatMessage("am i right?", jack.getId(), false,
                        new MessageDate(2018, 1, 1, 3, 20, 20)),
                new ChatMessage("hello", jack.getId(), false,
                        new MessageDate(2018, 1, 1, 3, 21, 20)),
                new ChatMessage("how are you?", john.getId(), false,
                        new MessageDate(2018, 1, 2, 3, 20, 20)),
                new ChatMessage("ok", john.getId(), false,
                        new MessageDate(208, 1, 2, 4, 20, 1)),
                new ChatMessage("good", john.getId(), false,
                        new MessageDate(2018, 1, 2, 4, 20, 51)),
                new ChatMessage("just now?", jozef.getId(), false,
                        new MessageDate(2018, 1, 1, 4, 20, 20)),
                new ChatMessage("where's john? are you know?", jozef.getId(), false,
                        new MessageDate(2018, 1, 1, 4, 21, 20))
                );

        when(messageRepository.findAll()).thenReturn(chatMessages);
        friends.forEach(f -> doReturn(lastFriendMessage(chatMessages, f.getId()))
                .when(messageRepository).findLastFriendMessage(f.getId()));
        when(friendRepository.findAll()).thenReturn(friends);


        final List<Message> messages = interactor.getMessages("john");
        assertEquals(messages.size(), 2);
        final Message lastJohnMessage = messages.get(0);
        final Message lastJozefMessage = messages.get(1);
        assertEquals(lastJohnMessage.getText(), "good");
        assertEquals(lastJozefMessage.getText(), "where's john? are you know?");
    }

    @Test
    public void getMessages_shouldReturnMessagesSortedByDate() {
        final Friend jack = new Friend("Jack", UUID.randomUUID());
        final Friend john = new Friend("John", UUID.randomUUID());
        final Friend jozef = new Friend("Jozef", UUID.randomUUID());
        final List<Friend> friends = Arrays.asList(jack, john, jozef);
        final List<ChatMessage> chatMessages = Arrays.asList(
                new ChatMessage("am i right?", jack.getId(), false,
                        new MessageDate(2017, 1, 1, 3, 20, 21)),
                new ChatMessage("hello", jack.getId(), false,
                        new MessageDate(2018, 1, 1, 3, 21, 30)),
                new ChatMessage("how are you?", john.getId(), false,
                        new MessageDate(2018, 1, 2, 3, 20, 10)),
                new ChatMessage("ok", john.getId(), false,
                        new MessageDate(2018, 1, 3, 4, 20, 11)),
                new ChatMessage("good", john.getId(), false,
                        new MessageDate(2018, 1, 3, 5, 20, 51)),
                new ChatMessage("just now?", jozef.getId(), false,
                        new MessageDate(2018, 1, 3, 5, 25, 24)),
                new ChatMessage("where's john? are you know?", jozef.getId(), false,
                        new MessageDate(2018, 1, 3, 5, 29, 20)),
                new ChatMessage("it's ok?", jozef.getId(), false,
                        new MessageDate(2018, 1, 3, 5, 29, 29))
        );

        when(messageRepository.findAll()).thenReturn(chatMessages);
        friends.forEach(f -> doReturn(lastFriendMessage(chatMessages, f.getId()))
                .when(messageRepository).findLastFriendMessage(f.getId()));
        when(friendRepository.findAll()).thenReturn(friends);

        final List<Message> messages = interactor.getMessages();
        assertEquals(messages.size(), 3);
        assertEquals(messages.get(0).getText(), "it's ok?");
        assertEquals(messages.get(1).getText(), "good");
        assertEquals(messages.get(2).getText(), "hello");
    }

    private Friend getById(List<Friend> friends, UUID id) {
        return friends.stream().filter(f -> f.getId().equals(id)).findFirst().get();
    }

    private ChatMessage lastFriendMessage(List<ChatMessage> friends, UUID id) {
        return friends.stream().filter(m -> m.getFriendId().equals(id)).reduce((m1, m2) -> m2).orElse(null);
    }

    @After
    public void after() {
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
