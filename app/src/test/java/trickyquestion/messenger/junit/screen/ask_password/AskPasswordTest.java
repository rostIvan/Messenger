package trickyquestion.messenger.junit.screen.ask_password;

import net.bytebuddy.utility.RandomString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordFragment;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordInteractor;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter.Screen;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AskPasswordTest {
    @Mock AskPasswordFragment askPasswordFragment;
    @Mock AuthPreference authPreference;
    AskPasswordInteractor interactor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubMethods();
        interactor = new AskPasswordInteractor(askPasswordFragment, authPreference);
    }

    private void stubMethods() {
        when(askPasswordFragment.router()).thenReturn(mock(ApplicationRouter.class));
        doNothing().when(askPasswordFragment).showError(any());
    }

    @Test
    public void inCaseCorrectPasswordEntered_shouldOpenMain() {
        for (int i = 1; i < 500; i++) {
            final VerificationMode times = times(i);
            final String randPassword = new RandomString().nextString();
            when(authPreference.getAccountPassword()).thenReturn(randPassword);

            interactor.dataProcess(randPassword);
            verify(askPasswordFragment.router(), times).finishActivity();
            verify(askPasswordFragment.router(), times).openScreen(Screen.MAIN);
            verify(askPasswordFragment, never()).showError(anyString());
        }
        passed("inCaseCorrectPasswordEntered");
    }

    @Test
    public void inCaseIncorrectPasswordEntered_shouldShowError() {
        for (int i = 1; i < 500; i++) {
            final VerificationMode times = times(i);
            String randPassword = new RandomString().nextString();
            while(randPassword.equals("123"))
                randPassword = new RandomString().nextString();
            when(authPreference.getAccountPassword()).thenReturn("123");

            interactor.dataProcess(randPassword);
            verify(askPasswordFragment, times).showError(anyString());
            verify(askPasswordFragment.router(), never()).finishActivity();
            verify(askPasswordFragment.router(), never()).openScreen(Screen.MAIN);
        }
        passed("inCaseIncorrectPasswordEntered");
    }

    @Test
    public void _3timesIncorrectOnceCorrect_shouldOpenMain() {
        for (int i = 1; i < 4; i++) {
            final VerificationMode times = times(i);
            String randPassword = new RandomString().nextString();
            while(randPassword.equals("123"))
                randPassword = new RandomString().nextString();
            when(authPreference.getAccountPassword()).thenReturn("123");

            interactor.dataProcess(randPassword);
            verify(askPasswordFragment, times).showError(anyString());
            verify(askPasswordFragment.router(), never()).finishActivity();
            verify(askPasswordFragment.router(), never()).openScreen(Screen.MAIN);
        }

        interactor.dataProcess("123");
        verify(askPasswordFragment.router(), times(1)).finishActivity();
        verify(askPasswordFragment.router(),  times(1)).openScreen(Screen.MAIN);
        passed("3timesIncorrectOnceCorrect");
    }

    @Test
    public void getGreeting_shouldReturnValidString() {
        when(authPreference.getAccountLogin()).thenReturn("Petya");
        final String greeting = interactor.getGreeting();
        final boolean keywordsContainName = greeting.contains("Hello, Petya");
        assertTrue(keywordsContainName);
        passed("getGreeting");
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
