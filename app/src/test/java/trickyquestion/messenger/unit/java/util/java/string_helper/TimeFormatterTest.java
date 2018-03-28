package trickyquestion.messenger.unit.java.util.java.string_helper;

import org.junit.Test;

import trickyquestion.messenger.util.java.string_helper.TimeFormatter;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

public class TimeFormatterTest {
    @Test
    public void convertTimeCorrect() {
        String dateForChange = "23:20:19";
        String changedDate = TimeFormatter.convertTime(dateForChange, "SS:mm:HH", "HH:mm:SS");
        assertTrue("19:20:23".equals(changedDate));
    }
}
