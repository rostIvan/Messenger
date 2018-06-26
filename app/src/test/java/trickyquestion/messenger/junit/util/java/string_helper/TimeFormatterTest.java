package trickyquestion.messenger.junit.util.java.string_helper;

import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import trickyquestion.messenger.util.java.string_helper.TimeFormatter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

public class TimeFormatterTest {

    @Test
    public void convertTimeCorrect() {
        String dateForChange = "23:20:19";
        String changedDate1 = TimeFormatter.convertTime(dateForChange, "SS:mm:HH", "HH:mm:SS");
        assertEquals("19:20:23", changedDate1);
        String changedDate2 = TimeFormatter.convertTime(dateForChange, "HH:mm:SS", "HH.mm.SS");
        assertEquals("23.20.19", changedDate2);
    }

    @Test
    public void inCaseIncorrectInput_shouldReturnNull() {
        String dateForChange = "23:20:.12";
        String changedDate = TimeFormatter.convertTime(dateForChange, "HH:mm:SS", "HH.mm.SS");
        assertNull(changedDate);
    }

    @Test
    public void currentTime_shouldReturnCorrectTime() {
        final Integer hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        final Integer minutes = Calendar.getInstance().get(Calendar.MINUTE);
        final Integer seconds = Calendar.getInstance().get(Calendar.SECOND);
        final String currentTime = TimeFormatter.getCurrentTime("HH:mm:ss");
        final List<Integer> values = Stream.of(currentTime.split(":"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println(values);
        System.out.println(hours + ":" + minutes + ":" + seconds);
        assertEquals(hours, values.get(0));
        assertEquals(minutes, values.get(1));
        assertEquals(seconds, values.get(2));
    }
}
