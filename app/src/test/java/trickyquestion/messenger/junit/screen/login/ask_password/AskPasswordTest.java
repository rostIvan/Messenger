package trickyquestion.messenger.junit.screen.login.ask_password;

import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordFragment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AskPasswordFragment.class})
public class AskPasswordTest {
    private AskPasswordFragment askPasswordFragment;

    @Before
    public void before() throws Exception {
        askPasswordFragment = mock(AskPasswordFragment.class);
        doCallRealMethod().when(askPasswordFragment).onButtonSignInClick();
        when(askPasswordFragment, "passIsCorrect").thenCallRealMethod();
    }

    @After
    public void after() throws Exception {
        log(LINE, Color.BLUE);
    }

    @Test
    public void inCaseCorrectPass_shouldSignInAccount() throws Exception {
        when(askPasswordFragment, "getEnteredPass").thenReturn("1234");
        when(askPasswordFragment, "getUserPassword").thenReturn("1234");
        askPasswordFragment.onButtonSignInClick();
        verifyPrivate(askPasswordFragment, times(0)).invoke("showError", any());
        verifyPrivate(askPasswordFragment, times(1)).invoke("signInAccount");
        passed("in case correct pass");
    }

    @Test
    public void inCaseIncorrectPass_shouldShowError() throws Exception {
        when(askPasswordFragment, "getEnteredPass").thenReturn("1235");
        when(askPasswordFragment, "getUserPassword").thenReturn("1234");
        askPasswordFragment.onButtonSignInClick();
        verifyPrivate(askPasswordFragment, times(0)).invoke("signInAccount");
        verifyPrivate(askPasswordFragment, times(1)).invoke("showError", any());
        passed("in case incorrect pass");
    }

    private void passed(final String text) {
        log(text + "  ==> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
