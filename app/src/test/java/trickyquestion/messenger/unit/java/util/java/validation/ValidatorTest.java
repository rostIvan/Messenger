package trickyquestion.messenger.unit.java.util.java.validation;

import org.junit.Test;

import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.java.validation.PassValidator;
import trickyquestion.messenger.util.java.validation.SignUpValidator;
import trickyquestion.messenger.util.java.validation.UserInputValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Nadiia Bogoslavets and Basil Polych on 24.02.2018.
 */


public class ValidatorTest {

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
    public void isPasswordValidatorCorrect (){
        String validPassword = "password11";
        assertTrue("Password validator work incorrect", UserInputValidator.isPasswordValid(validPassword));
        String tooSmallPassword = "11";
        assertTrue("Password validator work incorrect", !UserInputValidator.isPasswordValid(tooSmallPassword));
        String incorrectPassword = "пароль";
        assertTrue("Password validator work incorrect", !UserInputValidator.isPasswordValid(incorrectPassword));
    }

    @Test
    public void SignUpValidatorCorrect (){
        String validName = "Nadiia";
        String validPassword = "password11";
        String tooSmallPassword = "11";
        String incorrectName = "собака";
        assertTrue("Signing validator work incorrect", SignUpValidator.isCorrect(validName, validPassword));
        assertTrue("Signing validator work incorrect", !SignUpValidator.isCorrect(validName, tooSmallPassword));
        assertTrue("Signing validator work incorrect", !SignUpValidator.isCorrect(incorrectName, validPassword));
    }

    @Test
    public void PassValidatorrCorrect (){
        String realPass = "password11";
        String validenteredPass = "password11";
        String tooSmallenteredPass = "password";
        String incorrecenteredPass = "qwerty";
        assertTrue("Password validator work incorrect", PassValidator.isCorrect(validenteredPass,realPass));
        assertTrue("Password validator work incorrect", !PassValidator.isCorrect(tooSmallenteredPass,realPass));
        assertTrue("Password validator work incorrect", !PassValidator.isCorrect(incorrecenteredPass,realPass));
    }

    @Test
    public void passValidator_checkPreviousPassword_isCorrect(){
        final AuthPreference authPreference = mock(AuthPreference.class);
        when(authPreference.getAccountPassword()).thenReturn("123", "222", "333");
        assertEquals(UserInputValidator.isPreviousPasswordCorrect("123", authPreference), true);
        assertEquals(UserInputValidator.isPreviousPasswordCorrect("123", authPreference), false);
        assertEquals(UserInputValidator.isPreviousPasswordCorrect("333", authPreference), true);
    }

}
