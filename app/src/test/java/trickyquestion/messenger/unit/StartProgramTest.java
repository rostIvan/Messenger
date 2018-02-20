package trickyquestion.messenger.unit;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.application.entry_point.AppStarter;
import trickyquestion.messenger.util.Color;
import trickyquestion.messenger.util.Mode;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static trickyquestion.messenger.util.LoggerKt.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StartProgramTest {
    @Mock
    private Context context;
    @Mock
    private AuthPreference authPreference;
    @InjectMocks
    private AppStarter appStarter;

    @Before
    public void before() {
        appStarter = spy(new AppStarter(context));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void objects_isMocked() {
        assertTrue( MockUtil.isMock(context) );
        assertTrue( MockUtil.isMock(authPreference) );
        assertTrue( MockUtil.isMock(appStarter) );
        passed("objects mock");
    }

    @Test
    public void dataProvided_isCorrect() {
        doReturn(true, false, true).when(authPreference).askPassword();
        doReturn(false, false, true).when(authPreference).isUserAuthenticated();

        assertEquals(appStarter.askPasswordActivated(), true);
        assertEquals(appStarter.askPasswordActivated(), false);
        assertEquals(appStarter.askPasswordActivated(), true);

        assertEquals(appStarter.isAuthenticated(), false);
        assertEquals(appStarter.isAuthenticated(), false);
        assertEquals(appStarter.isAuthenticated(), true);
        passed("data provided");
    }

    @Test
    public void loginMethod_shouldCalled() {
        when(authPreference.isUserAuthenticated()).thenReturn(false);
        when(authPreference.askPassword()).thenReturn(false);
        appStarter.start();

        verify(appStarter, times(0)).askPassword();
        verify(appStarter, times(0)).main();
        verify(appStarter, times(0)).connect();
        verify(appStarter, times(1)).login();
        passed("login start");
    }

    @Test
    public void askPassMethod_shouldCalled() {
        when(authPreference.isUserAuthenticated()).thenReturn(true);
        when(authPreference.askPassword()).thenReturn(true);
        appStarter.start();

        verify(appStarter, times(0)).login();
        verify(appStarter, times(0)).main();
        verify(appStarter, times(1)).connect();
        verify(appStarter, times(1)).askPassword();
        passed("askPassword start with connection");
    }

    @Test
    public void mainMethod_shouldCalled() {
        when(authPreference.isUserAuthenticated()).thenReturn(true);
        when(authPreference.askPassword()).thenReturn(false);
        appStarter.start();

        verify(appStarter, times(0)).login();
        verify(appStarter, times(0)).askPassword();
        verify(appStarter, times(1)).connect();
        verify(appStarter, times(1)).main();
        passed("main start with connection");
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ==> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
