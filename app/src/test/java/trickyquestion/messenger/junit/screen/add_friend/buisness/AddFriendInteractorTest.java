package trickyquestion.messenger.junit.screen.add_friend.buisness;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import trickyquestion.messenger.buisness.DataProvider;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.buisness.IAddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.data.User;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordInteractor;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddFriendInteractorTest {

    @Mock private DataProvider dataProvider;
    @Mock private FriendRepository friendRepository;
    private IAddFriendInteractor interactor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        interactor = new AddFriendInteractor(dataProvider, friendRepository);
    }

    @Test
    public void ifNetworkContainFriends_shouldReturnFilteredList() {
        final UUID id1 = UUID.randomUUID();
        final UUID id2 = UUID.randomUUID();
        final UUID id3 = UUID.randomUUID();
        final List<IUser> fromNetwork = Arrays.asList(
                new User("1", id1),
                new User("2", id2),
                new User("3", id3));
        final List<Friend> fromDatabase = Arrays.asList(
                new Friend("1", id1),
                new Friend("2", id2));
        when(dataProvider.getUsersFromNetwork()).thenReturn(fromNetwork);
        when(dataProvider.getFriendsFromDb()).thenReturn(fromDatabase);
        final List<IUser> users = interactor.getUsers();
        assertEquals(users.size(), 1);
        final IUser user = interactor.getUsers().get(0);
        assertEquals(user.getId(), id3);
        passed("ifNetworkContainFriends");
    }

    @Test
    public void ifNetworkEmpty_shouldReturnEmptyList() {
        final UUID id1 = UUID.randomUUID();
        final UUID id2 = UUID.randomUUID();
        final List<IUser> fromNetwork = Collections.emptyList();
        final List<Friend> fromDatabase = Arrays.asList(new Friend("1", id1),
                new Friend("2", id2));
        when(dataProvider.getUsersFromNetwork()).thenReturn(fromNetwork);
        when(dataProvider.getFriendsFromDb()).thenReturn(fromDatabase);
        final List<IUser> users = interactor.getUsers();
        assertTrue(users.isEmpty());
        passed("ifNetworkEmpty");
    }

    @Test
    public void ifInNetworkOnlyFriends_shouldReturnEmptyList() {
        final UUID id1 = UUID.randomUUID();
        final UUID id2 = UUID.randomUUID();
        final UUID id3 = UUID.randomUUID();
        final List<IUser> fromNetwork = Arrays.asList(
                new User("1", id1),
                new User("2", id2),
                new User("3", id3));
        final List<Friend> fromDatabase = Arrays.asList(
                new Friend("1", id1),
                new Friend("2", id2),
                new Friend("3", id3));
        when(dataProvider.getUsersFromNetwork()).thenReturn(fromNetwork);
        when(dataProvider.getFriendsFromDb()).thenReturn(fromDatabase);
        final List<IUser> users = interactor.getUsers();
        assertTrue(users.isEmpty());
        passed("ifInNetworkOnlyFriends");
    }

    @Test
    public void ifFriendsNotAdded_shouldReturnAllFromNetwork() {
        final UUID id1 = UUID.randomUUID();
        final UUID id2 = UUID.randomUUID();
        final UUID id3 = UUID.randomUUID();
        final List<IUser> fromNetwork = Arrays.asList(new User("1", id1),
                new User("2", id2), new User("3", id3));
        final List<Friend> fromDatabase = Collections.emptyList();
        when(dataProvider.getUsersFromNetwork()).thenReturn(fromNetwork);
        when(dataProvider.getFriendsFromDb()).thenReturn(fromDatabase);
        final List<IUser> users = interactor.getUsers();
        assertFalse(users.isEmpty());
        assertEquals(users.size(), 3);
        passed("ifFriendNotAdded");
    }

    @Test
    public void addFriend_shouldSaveFriendInDb() {
        final User user = new User("Andrew", UUID.randomUUID());
        interactor.addToFriends(user);
        verify(friendRepository).save(new Friend(user.getName(), user.getId(), Friend.Status.ONLINE));
        passed("addFriend");
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
