package trickyquestion.messenger.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import trickyquestion.messenger.screen.login.sign_up.SignUpActivity;
import trickyquestion.messenger.screen.login.sign_up.SignUpFragment;
import trickyquestion.messenger.util.Color;
import trickyquestion.messenger.util.Mode;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static trickyquestion.messenger.util.LoggerKt.LINE;
import static trickyquestion.messenger.util.LoggerKt.log;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SignUpFragment.class, SignUpActivity.class})
public class SignUpTest {

    private SignUpFragment signUpFragment;

    @Before
    public void before() throws Exception {
        signUpFragment = mock(SignUpFragment.class);
        doCallRealMethod().when(signUpFragment).createAccount();
        when(signUpFragment, method(SignUpFragment.class, "isEnteredCorrect"))
                .withArguments(anyString(), anyString()).thenCallRealMethod();
    }

    @After
    public void after() throws Exception {
        log(LINE, Color.BLUE);
    }

    @Test
    public void inCaseSignUpData_isCorrect() throws Exception {
        final String enteredLogin = "john";
        final String enteredPassword = "1234";
        when(signUpFragment, "getEnteredLogin").thenReturn(enteredLogin);
        when(signUpFragment, "getEnteredPassword").thenReturn(enteredPassword);
        signUpFragment.createAccount();
        verify(signUpFragment, times(0)).showError(anyString());
        verifyPrivate(signUpFragment, times(1)).invoke(method(SignUpFragment.class, "createAccountWithData"))
                .withArguments(enteredLogin, enteredPassword);
        passed("in case sing up data is correct");
    }

    @Test
    public void inCaseSignUpData_isNotCorrect() throws Exception {
        final String enteredLogin = "john";
        final String enteredPassword = "j9";
        when(signUpFragment, "getEnteredLogin").thenReturn(enteredLogin);
        when(signUpFragment, "getEnteredPassword").thenReturn(enteredPassword);
        signUpFragment.createAccount();
        verifyPrivate(signUpFragment, times(0)).invoke(method(SignUpFragment.class, "createAccountWithData"))
                .withArguments(enteredLogin, enteredPassword);
        verify(signUpFragment, times(1)).showError(anyString());
        passed("in case sing up data isn't correct");
    }


    private void passed(final String text) {
        log(text + "  ==> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
