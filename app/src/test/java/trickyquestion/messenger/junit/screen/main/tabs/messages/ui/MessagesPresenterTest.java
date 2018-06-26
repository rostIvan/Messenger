package trickyquestion.messenger.junit.screen.main.tabs.messages.ui;

import android.os.Bundle;
import android.view.View;

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

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsPresenter;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesEventManager;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesPresenter;
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesView;
import trickyquestion.messenger.screen.main.tabs.messages.ui.MessagesPresenter;
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
public class MessagesPresenterTest {

    @Mock IMessagesView view;
    @Mock BaseRouter router;
    @Mock IMessagesInteractor interactor;
    @Mock MessagesEventManager eventManager;
    IMessagesPresenter presenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doAnswer(invocation -> {
            final Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(view).onUiThread(any());
        presenter = new MessagesPresenter(view, router, interactor);
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
        verify(view).showMessages(any());
        passed("onViewCreated");
    }

    @Test
    public void updateMessages_shouldUpdateUI() {
        final List<Message> messages = Arrays.asList(new Message("Hi"), new Message("Hello"));
        when(interactor.getMessages()).thenReturn(messages);
        presenter.updateMessages();
        verify(view).showMessages(messages);
        passed("updateMessages");
    }

    @Test
    public void onSearchTextChange_shouldUpdateUI() {
        presenter.onQueryTextChanged("Fibo");
        verify(interactor).getMessages("Fibo");
        verify(view).showMessages(any());
        passed("onTextChange");
    }

    @Test
    public void onImageClick_shouldShowProfilePhoto() {
        final Message message = new Message("Hello", new Friend("Chuck", UUID.randomUUID()), null);
        presenter.onFriendPhotoClick(message);
        verify(view).showFriendPhotoDialog(any(Bundle.class));
        passed("onImageClick");
    }

    @Test
    public void onItemClick_shouldOpenChat() {
        final Message message = new Message("Hello", new Friend("Chuck", UUID.randomUUID()), null);
        presenter.onMessageItemClick(message);
        verify(router).openScreen(
                eq(BaseRouter.Screen.CHAT), any(Bundle.class), any(AnimatorResource.class));
        passed("onItemClick");
    }

    @Test
    public void onRefresh_shouldShowProgressBar() {
        presenter.onRefresh();
        verify(view).showProgress(true);
        passed("onRefresh");
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
