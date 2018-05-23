package trickyquestion.messenger.junit.application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.application.ApplicationStarter;
import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ApplicationStarterTest {
    @Mock private ApplicationRouter router;
    @Mock private AuthPreference authPreference;
    @Mock private P2PConnector p2pConnector;
    private ApplicationStarter applicationStarter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(p2pConnector).connect();
        applicationStarter = new ApplicationStarter(authPreference, router, p2pConnector);
    }

    @Test
    public void inCaseUserNotAuth_shouldOpenSignUpScreen() {
        when(authPreference.isUserAuthenticated()).thenReturn(false);
        applicationStarter.start();
        verify(router, only()).openScreen(BaseRouter.Screen.SIGN_UP);
        verify(router, never()).openScreen(BaseRouter.Screen.MAIN);
        verify(router, never()).openScreen(BaseRouter.Screen.ASK_PASSWORD);
        verify(p2pConnector, never()).connect();
        passed("inCaseUserNotAuth");
    }

    @Test
    public void inCaseUserAuth_shouldOpenMainScreen() {
        when(authPreference.isUserAuthenticated()).thenReturn(true);
        when(authPreference.askPassword()).thenReturn(false);
        applicationStarter.start();
        verify(router, only()).openScreen(BaseRouter.Screen.MAIN);
        verify(router, never()).openScreen(BaseRouter.Screen.SIGN_UP);
        verify(router, never()).openScreen(BaseRouter.Screen.ASK_PASSWORD);
        verify(p2pConnector, only()).connect();
        passed("inCaseUserAuth");
    }

    @Test
    public void inCaseUserAuthWithAskPassword_shouldOpenMainScreen() {
        when(authPreference.isUserAuthenticated()).thenReturn(true);
        when(authPreference.askPassword()).thenReturn(true);
        applicationStarter.start();
        verify(router, only()).openScreen(BaseRouter.Screen.ASK_PASSWORD);
        verify(router, never()).openScreen(BaseRouter.Screen.SIGN_UP);
        verify(router, never()).openScreen(BaseRouter.Screen.MAIN);
        verify(p2pConnector, only()).connect();
        passed("inCaseUserAuthWithAskPassword");
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
