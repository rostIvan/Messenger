package trickyquestion.messenger.unit.java.util.java.validation;

import org.junit.Test;

import trickyquestion.messenger.util.java.validation.SignUpValidator;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

public class SignUpValidatorTest {
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
}
