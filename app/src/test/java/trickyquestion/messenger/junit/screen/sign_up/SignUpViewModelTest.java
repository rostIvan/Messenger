package trickyquestion.messenger.junit.screen.sign_up;

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
import trickyquestion.messenger.screen.login.sign_up.SignUpViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SignUpViewModelTest {
    @Mock private SignUpViewModel.SignUpCallback callback;
    private SignUpViewModel signUpViewModel;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        signUpViewModel = new SignUpViewModel(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseLoginNull_shouldThrowException() {
        passed("inCaseLoginNull");
        signUpViewModel.onSignUpButtonClick(null, "12311131551", callback);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCasePassNull_shouldThrowException() {
        passed("inCasePassNull");
        signUpViewModel.onSignUpButtonClick("Kolya", null, callback);
    }

    @Test
    public void inCaseIncorrectData_shouldCallOnError() {
        signUpViewModel.onSignUpButtonClick("", "", callback);
        verify(callback, only()).onError();
        passed("inCaseIncorrectData");
    }

    @Test
    public void inCaseCorrectData_shouldCallOnSuccess() {
        signUpViewModel.onSignUpButtonClick("Vasya", "1234", callback);
        verify(callback, only()).onSuccess();
        passed("inCaseCorrectData");
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
