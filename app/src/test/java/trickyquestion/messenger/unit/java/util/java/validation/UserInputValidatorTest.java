package trickyquestion.messenger.unit.java.util.java.validation;

import org.junit.Test;

import trickyquestion.messenger.util.java.validation.UserInputValidator;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

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
}
