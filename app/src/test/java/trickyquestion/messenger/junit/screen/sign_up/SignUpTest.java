package trickyquestion.messenger.junit.screen.sign_up;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.util.UUID;

import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.login.sign_up.SignUpFragment;
import trickyquestion.messenger.screen.login.sign_up.SignUpInteractor;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter.Screen;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SignUpTest {

    @Mock SignUpFragment signUpFragment;
    @Mock P2PConnector p2pConnector;
    @Mock AuthPreference authPreference;
    SignUpInteractor interactor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubMethods();
        interactor = new SignUpInteractor(signUpFragment, authPreference, p2pConnector);
    }

    private void stubMethods() {
        when(signUpFragment.router()).thenReturn(mock(ApplicationRouter.class));
        doNothing().when(signUpFragment).showError(any());
    }

    @Test
    public void inCaseCorrectLoginAndPass_shouldCreateAccount() {
        final VerificationMode once = times(1);
        final String login = "Vasya";
        final String password = "qwerty12345";

        interactor.dataProcess(login, password);

        verify(signUpFragment.router(), once).finishActivity();
        verify(signUpFragment.router(), once).openScreen(Screen.MAIN);
        verify(p2pConnector, once).connect();
        verify(authPreference, once).setAccountId(any());
        verify(authPreference, once).setAccountData(login, password);
        verify(authPreference, once).setUserAuthenticated(true);
        verify(signUpFragment, never()).showError(any());
        passed("inCaseCorrectLoginAndPass");
    }

    @Test
    public void inCaseIncorrectLoginAndPass_shouldShowError() {
        final VerificationMode once = times(1);
        final String login = "V1";
        final String password = "qwerty1234";

        interactor.dataProcess(login, password);

        verify(signUpFragment, once).showError(any());
        verify(signUpFragment.router(), never()).finishActivity();
        verify(signUpFragment.router(), never()).openScreen(Screen.MAIN);
        verify(p2pConnector, never()).connect();
        verify(authPreference, never()).setAccountId(any());
        verify(authPreference, never()).setAccountData(login, password);
        verify(authPreference, never()).setUserAuthenticated(true);
        passed("inCaseIncorrectLogin");
    }
@Test
    public void inCaseIncorrectPass_shouldShowError() {
        final VerificationMode once = times(1);
        final String login = "Petya";
        final String password = "11";

        interactor.dataProcess(login, password);

        verify(signUpFragment, once).showError(any());
        verify(signUpFragment.router(), never()).finishActivity();
        verify(signUpFragment.router(), never()).openScreen(Screen.MAIN);
        verify(p2pConnector, never()).connect();
        verify(authPreference, never()).setAccountId(any());
        verify(authPreference, never()).setAccountData(login, password);
        verify(authPreference, never()).setUserAuthenticated(true);
        passed("inCaseIncorrectPass");
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
