package trickyquestion.messenger.junit.screen.main.tabs.messages.buisness;

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

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesEventManager;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesPresenter;
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesView;
import trickyquestion.messenger.screen.main.tabs.messages.ui.MessagesPresenter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeMessageDbEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MessagesEventManagerTest {

    @Mock IMessagesView view;
    @Mock BaseRouter router;
    @Mock IMessagesInteractor interactor;
    IMessagesPresenter presenter;
    MessagesEventManager eventManager;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doAnswer(invocation -> {
            final Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(view).onUiThread(any());
        presenter = new MessagesPresenter(view, router, interactor);
        eventManager = new MessagesEventManager(presenter);
        presenter.attach(eventManager);
    }

    @Test
    public void onEventChangeFriendDb_shouldDeleteMessageDeletedFriends() {
        final ChangeFriendDbEvent friendDbEvent = new ChangeFriendDbEvent("Change db friend");
        eventManager.onEvent(friendDbEvent);
        verify(interactor).deleteEmptyTables();
        passed("onEventChangeDb");
    }

    @Test
    public void onEventChangeMessageDb_shouldUpdateUI() {
        final List<Message> messages = Arrays.asList(new Message("1"), new Message("2"));
        when(interactor.getMessages()).thenReturn(messages);

        final ChangeMessageDbEvent messageDbEvent = new ChangeMessageDbEvent("Change db messages");
        eventManager.onEvent(messageDbEvent);
        verify(view).showMessages(messages);
        passed("onEventChangeDb");
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
