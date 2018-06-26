package trickyquestion.messenger.junit.screen.main.container;

import android.view.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.main.container.implementation.MainEventManager;
import trickyquestion.messenger.screen.main.container.implementation.MainPresenter;
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter;
import trickyquestion.messenger.screen.main.container.interfaces.IMainView;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.util.AnimatorResource;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MainPresenterTest {

    @Mock IMainView view;
    @Mock BaseRouter router;
    MainEventManager eventManager;
    IMainPresenter presenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(view, router);
        eventManager = spy(new MainEventManager(presenter));
        presenter.attach(eventManager);
    }

    @Test
    public void onCreate_shouldShowContentAndSubscribe() {
        presenter.onCreate(null);
        verify(eventManager).subscribe();
        verify(view).showContent();
        passed("onCreate");
    }

    @Test
    public void onDestroy_shouldUnsubscribeEventManager() {
        presenter.onDestroy();
        verify(eventManager).unsubscribe();
        passed("onDestroy");
    }

    @Test
    public void allListeners_shouldWorkProperly() {
        presenter.onFabClick();
        verify(router).openScreen(BaseRouter.Screen.ADD_FRIEND);

        presenter.onAccountClick();
        verify(view).displayAccountPopup(true);

        presenter.onSettingsClick();
        verify(router).openScreen(eq(BaseRouter.Screen.SETTINGS), any(AnimatorResource.class));

        presenter.onKeyDown(KeyEvent.KEYCODE_MENU, any());
        verify(router).openScreen(BaseRouter.Screen.SETTINGS);

        presenter.onPageSelected(0);
        verify(view).displayFab(true);
        verify(view, never()).displayFab(false);

        presenter.onPageSelected(1);
        verify(view).displayFab(false);
        passed("allListeners");
    }

    @Test
    public void onChangeThemeEvent_shouldRefreshView() {
        final ChangeThemeEvent event = new ChangeThemeEvent(android.graphics.Color.RED);
        eventManager.onEvent(event);
        verify(view).refreshTheme();
        passed("onChangeThemeEvent");
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
        presenter = null;
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
