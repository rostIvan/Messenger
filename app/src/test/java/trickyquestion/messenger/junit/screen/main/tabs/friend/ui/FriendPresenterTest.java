package trickyquestion.messenger.junit.screen.main.tabs.friend.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsEventManager;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.IFriendsInteractor;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsPresenter;
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsPresenter;
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsView;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.util.AnimatorResource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FriendPresenterTest {
    @Mock IFriendsView view;
    @Mock IFriendsInteractor interactor;
    @Mock BaseRouter router;
    @Mock FriendsEventManager eventManager;
    IFriendsPresenter presenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doAnswer(invocation -> {
            final Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(view).onUiThread(any());
        presenter = new FriendsPresenter(view, router, interactor);
        presenter.attach(eventManager);
    }

    @Test
    public void subscribeAndUnsubscribe_shouldCalledInLifecycle() {
        presenter.onCreateView(null, null, null);
        verify(eventManager).subscribe();

        presenter.onDestroyView();
        verify(eventManager).unsubscribe();
        passed("subscribeAndUnsubscribe");
    }

    @Test
    public void onViewCreated_shouldShowFriends() {
        presenter.onViewCreated(mock(View.class), null);
        verify(view).showFriends(any());
        passed("onViewCreated");
    }

    @Test
    public void updateFriend_shouldUpdateUI() {
        final List<Friend> friends = Collections.singletonList(
                new Friend("Fibonacci", UUID.randomUUID()));
        when(interactor.getFriends()).thenReturn(friends);
        presenter.updateFriends();
        verify(view).showFriends(friends);
        passed("updateFriend");
    }

    @Test
    public void onSearchTextChange_shouldUpdateUI() {
        presenter.onQueryTextChanged("Fibo");
        verify(interactor).getFriends("Fibo");
        verify(view).showFriends(any());
        passed("onTextChange");
    }

    @Test
    public void onImageClick_shouldShowProfilePhoto() {
        final Friend friend = new Friend("Jack", UUID.randomUUID());
        presenter.onFriendImageClick(friend);
        verify(view).showFriendPhoto(any(Bundle.class));
        passed("onImageClick");
    }

    @Test
    public void onItemClick_shouldOpenChat() {
        final Friend friend = new Friend("Jack", UUID.randomUUID());
        presenter.onFriendItemClick(friend, Collections.emptyList());
        verify(router).openScreen(
                eq(BaseRouter.Screen.CHAT), any(Bundle.class), any(AnimatorResource.class));
        passed("onItemClick");
    }

    @Test
    public void onDeleteFriendClick_shouldCallInteractorMethod() {
        final Friend friend = new Friend("Jack", UUID.randomUUID());
        presenter.onFriendRemoveClick(friend);
        verify(interactor).deleteFriend(friend);
        passed("onDeleteFriendClick");
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
