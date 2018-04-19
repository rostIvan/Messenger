package trickyquestion.messenger.junit.util.java.string_helper;

import org.junit.Test;

import trickyquestion.messenger.util.java.string_helper.FixedString;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 24.02.2018.
 */

public class FixedStringTest {
    @Test
    public void fixedStringCorrect () {
        assertTrue("FixedString fill not work", FixedString.fill("Anna", '$', 10).equals("Anna$$$$$$"));
        assertTrue("FixedString toDynamicSize not work", FixedString.toDynamicSize("Anna$$$$$$", '$').equals("Anna"));
    }
}
