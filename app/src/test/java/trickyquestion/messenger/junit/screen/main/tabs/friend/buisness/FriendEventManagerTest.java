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
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.objects.OUser;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsEventManager;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.IFriendsInteractor;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsPresenter;
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsPresenter;
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsView;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FriendEventManagerTest {

    @Mock IFriendsInteractor interactor;
    @Mock IFriendsView view;
    IFriendsPresenter presenter;
    FriendsEventManager eventManager;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doAnswer(invocation -> {
            final Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(view).onUiThread(any());
        presenter = new FriendsPresenter(view, null, interactor);
        eventManager = new FriendsEventManager(presenter);
    }

    @Test
    public void onEventUpdateDb_shouldUpdateUI() {
        final List<Friend> friends = Arrays.asList(new Friend(), new Friend(), new Friend());
        when(interactor.getFriends()).thenReturn(friends);

        final ChangeFriendDbEvent changeFriendDb = new ChangeFriendDbEvent("Db is changed");
        eventManager.onEvent(changeFriendDb);

        verify(view).showFriends(friends);
        passed("onEventUpdateDb");
    }

    @Test
    public void onEventActiveNetwork_shouldShowFriends() {
        final ENetworkStateChanged event = new ENetworkStateChanged(NetworkState.ACTIVE);
        eventManager.onEvent(event);

        verify(view).showFriends(any());
        verify(view, never()).showToast(any());
        passed("onEventActiveNetwork");
    }

    @Test
    public void onEventInactiveNetwork_shouldShowToast() {
        final ENetworkStateChanged event = new ENetworkStateChanged(NetworkState.INACTIVE);
        eventManager.onEvent(event);

        verify(view, never()).showFriends(any());
        verify(view).showToast("You haven't connection");
        passed("onEventInactiveNetwork");
    }

    @Test
    public void onEventUserOffline_shouldUpdateStatus() {
        final IUser user = new OUser(UUID.randomUUID(), "Dave", null, null);
        eventManager.onEvent(new ChangeUserList(user, false));
        verify(interactor).updateFriendStatus(user, false);
        passed("onEventUserOffline");
    }

    @Test
    public void onEventUserOnline_shouldUpdateStatus() {
        final IUser user = new OUser(UUID.randomUUID(), "Dave", null, null);
        eventManager.onEvent(new ChangeUserList(user, true));
        verify(interactor).updateFriendStatus(user, true);
        passed("onEventUserOnline");
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
