package trickyquestion.messenger.junit.screen.main.tabs.friend.buisness;

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

import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.p2p_protocol.objects.OUser;
import trickyquestion.messenger.screen.add_friend.data.User;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsInteractor;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FriendInteractorTest {

    @Mock FriendRepository friendRepository;
    FriendsInteractor interactor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        interactor = new FriendsInteractor(friendRepository);
    }

    @Test
    public void getFriendsIfDbEmpty_shouldReturnEmptyList() {
        final List<Friend> mockFriends = Collections.emptyList();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final List<Friend> friends = interactor.getFriends();
        assertNotNull(friends);
        assertTrue(friends.isEmpty());
        passed("getFriendsIfDbEmpty");
    }

    @Test
    public void getFriends_shouldReturnListFriendFromDb() {
        final List<Friend> mockFriends = mockFriends();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final List<Friend> friends = interactor.getFriends();
        assertNotNull(friends);
        assertEquals(mockFriends.size(), friends.size());
        assertEquals(mockFriends, friends);
        passed("getFriends");
    }

    @Test
    public void getFriendsByQuery_shouldReturnFilteredResult() {
        final List<Friend> mockFriends = mockFriends();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final List<Friend> friends = interactor.getFriends("robby");
        final Friend friendByQuery = friends.get(0);
        assertEquals(friends.size(), 1);
        assertEquals(friendByQuery.getName(), "robby");
        assertEquals(friendByQuery.isOnline(), false);
        passed("getFriendsByQuery");
    }

    @Test
    public void delete_shouldCalledRepositoryMethod() {
        final List<Friend> mockFriends = mockFriends();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final Friend friend = new Friend("jack", UUID.randomUUID(), false);
        interactor.deleteFriend(friend);
        verify(friendRepository, times(1)).delete(friend);
        passed("delete");
    }

    @Test
    public void ifNotFriendUpdate_shouldNotUpdating() {
        final List<Friend> mockFriends = mockFriends();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final Friend friend = new Friend("Rand User", UUID.randomUUID());
        interactor.updateFriendStatus(toUser(friend), false);
        verify(friendRepository, never()).updateFriendStatus(friend, false);
        passed("ifNotFriendUpdate");
    }

    @Test
    public void ifFriendUpdate_shouldUpdateStatus() {
        final List<Friend> mockFriends = mockFriends();
        when(friendRepository.findAll()).thenReturn(mockFriends);
        final Friend friend = mockFriends.get(0);
        interactor.updateFriendStatus(toUser(friend), false);
        verify(friendRepository, times(1)).updateFriendStatus(friend, false);
        passed("ifFriendUpdate");
    }

    @Test
    public void ifFriendListEmpty_shouldNotUpdate() {
        when(friendRepository.findAll()).thenReturn(Collections.emptyList());
        interactor.updateFriendStatus(new OUser(null, null, null, null), true);
        verify(friendRepository, never()).updateFriendStatus(any(Friend.class), anyBoolean());
        passed("ifFriendListEmpty");
    }

    private User toUser(Friend friend) {
        return new User(friend.getName(), friend.getId(), null);
    }

    private List<Friend> mockFriends() {
        return Arrays.asList(
                new Friend("nick", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("john", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("leyla", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("fred", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("robby", UUID.randomUUID(), Friend.Status.OFFLINE));
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
