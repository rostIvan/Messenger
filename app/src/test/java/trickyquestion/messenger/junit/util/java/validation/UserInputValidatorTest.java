package trickyquestion.messenger.junit.util.java.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.java.validation.UserInputValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserInputValidatorTest {
    @Test
    public void isLoginValidatorCorrect (){
        String validLogin = "qwerty12";
        assertTrue("Login validator work incorrect", UserInputValidator.isLoginValid(validLogin));
        String tooSmallLogin = "qw";
        assertTrue("Login validator work incorrect", !UserInputValidator.isLoginValid(tooSmallLogin));
        String incorrectLogin = "тест";
        assertTrue("Login validator work incorrect", !UserInputValidator.isLoginValid(incorrectLogin));
    }

    @Test
    public void isPasswordValidatorCorrect () {
        String validPassword = "password11";
        assertTrue("Password validator work incorrect", UserInputValidator.isPasswordValid(validPassword));
        String tooSmallPassword = "11";
        assertTrue("Password validator work incorrect", !UserInputValidator.isPasswordValid(tooSmallPassword));
        String incorrectPassword = "пароль";
        assertTrue("Password validator work incorrect", !UserInputValidator.isPasswordValid(incorrectPassword));
    }

    @Test
    public void isPreviousPasswordCorrect() {
        final AuthPreference authPreference = mock(AuthPreference.class);
        when(authPreference.getAccountPassword()).thenReturn("123");
        final boolean correct = UserInputValidator.isPreviousPasswordCorrect("123", authPreference);
        assertTrue(correct);
        final boolean incorrect = UserInputValidator.isPreviousPasswordCorrect("1234", authPreference);
        assertFalse(incorrect);
    }
}
