package trickyquestion.messenger.junit.util.java.validation;

import org.junit.Test;

import trickyquestion.messenger.util.java.validation.PassValidator;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

public class PassValidatorTest {
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
}
