package trickyquestion.messenger.junit.util.java.maping;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.buisness.DataProvider;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.objects.OUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.util.java.maping.TypeMapping;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TypeMappingTest {

    @Mock DataProvider dataProvider;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mapToIFriend_shouldReturnValidObject() {
        final List<IUser> users = getMockUsersFromNetwork();
        when(dataProvider.getUsersFromNetwork()).thenReturn(users);

        final IUser user = users.get(0);
        final Friend friend = new Friend(user.getName(), user.getId());

        final IFriend mappedFriend = TypeMapping.toIFriend(friend, dataProvider);
        assertEquals(mappedFriend.getID(), user.getId());
        assertEquals(mappedFriend.getNetworkAddress(), user.getNetworkAddress());
        passed("mapToIFriend");
    }

    @Test
    public void mapToIFriendIfNetworkEmpty_shouldReturnValidObject() {
        final List<IUser> users = Collections.emptyList();
        when(dataProvider.getUsersFromNetwork()).thenReturn(users);
        final Friend friend = new Friend("nick", UUID.randomUUID(), Friend.Status.OFFLINE);
        final IFriend iFriend = TypeMapping.toIFriend(friend, dataProvider);
        final String ip =iFriend.getNetworkAddress();
        assertNull(ip);
        passed("mapToIFriendIfNetworkEmpty");
    }

    @Test
    public void mapToIFriendIfNotOnline_shouldReturnValidObject() {
        final List<IUser> users = getMockUsersFromNetwork();
        when(dataProvider.getUsersFromNetwork()).thenReturn(users);

        UUID id;
        do { id = UUID.randomUUID(); } while (containsSameId(id));

        final Friend friend = new Friend("Kolya", id);
        final IFriend iFriend = TypeMapping.toIFriend(friend, dataProvider);
        assertNull(iFriend.getNetworkAddress());
        passed("mapToIFriendIfNotOnline");
    }


    @Test
    public void mappingIFriendList_shouldReturnCorrectData() {
        final List<IUser> users = getMockUsersFromNetwork();
        when(dataProvider.getUsersFromNetwork()).thenReturn(users);

        final List<Friend> friends = Arrays.asList(
                new Friend("Petya", UUID.randomUUID()),
                new Friend("Vasya", UUID.randomUUID()),
                new Friend("Ilon", UUID.randomUUID()),
                new Friend("Nicka", UUID.randomUUID()));
        final List<IFriend> iFriends = TypeMapping.toIFriendList(friends, dataProvider);
        for (int i = 0; i < iFriends.size(); i++) {
            assertEquals(friends.get(i).getId(), iFriends.get(i).getID());
        }

        final List<Friend> emptyFriendList = Collections.emptyList();
        final List<IFriend> list = TypeMapping.toIFriendList(emptyFriendList, dataProvider);
        assertTrue(list.isEmpty());
        passed("mappingIFriendList");
    }

    @NonNull
    private List<IUser> getMockUsersFromNetwork() {
        return Arrays.asList(
                new OUser(UUID.randomUUID(), "Petya", null, Calendar.getInstance().getTime()),
                new OUser(UUID.randomUUID(), "Olya", null, Calendar.getInstance().getTime()),
                new OUser(UUID.randomUUID(), "Yulya", null, Calendar.getInstance().getTime()),
                new OUser(UUID.randomUUID(), "Vasya", null, Calendar.getInstance().getTime())
        );
    }

    private boolean containsSameId(UUID id) {
        for (IUser user : dataProvider.getUsersFromNetwork()) {
            if (user.getId().equals(id)) return true;
        }
        return false;
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
