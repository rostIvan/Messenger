package trickyquestion.messenger.junit.screen.main.tabs.messages.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.UUID;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.screen.main.tabs.messages.data.MessageUtil;

import static junit.framework.Assert.assertEquals;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(JUnit4.class)
public class MessageUtilTest {

    @Before
    public void before() {
    }

    @Test
    public void formattedTimeValue_shouldReturnValidString() {
        final String biggerTen = MessageUtil.formattedTimeValue(40);
        assertEquals(biggerTen, "40");
        final String equalTen = MessageUtil.formattedTimeValue(10);
        assertEquals(equalTen, "10");
        final String lessTen1 = MessageUtil.formattedTimeValue(9);
        assertEquals(lessTen1, "09");
        final String lessTen2 = MessageUtil.formattedTimeValue(2);
        assertEquals(lessTen2, "02");
        passed("formattedTimeValue");
    }

    @Test
    public void ifTodayDate_shouldReturnCorrectFormattedString() {
        final MessageDate current = MessageDate.getCurrent();

        String hours = (current.getHour() < 10) ? "0" + current.getHour() : "" + current.getHour();
        String minutes = (current.getMinute() < 10) ? "0" + current.getMinute() : "" + current.getMinute();
        String seconds = (current.getSecond() < 10) ? "0" + current.getSecond() : "" + current.getSecond();

        final String expected1 = MessageUtil.getTime(new Message("hello", new Friend(), current));
        final String expected2 = MessageUtil.getTime(
                new ChatMessage("hi", UUID.randomUUID(), true, current));
        final String expected3 = MessageUtil.formattedTime(current, Calendar.getInstance());
        final String actual = String.format("%s:%s:%s", hours, minutes, seconds);
        assertEquals(expected1, actual);
        assertEquals(expected2, actual);
        assertEquals(expected3, actual);
        passed("ifTodayDate");
    }

    @Test
    public void ifNotTodayDate_shouldReturnCorrectFormattedString() {
        MessageDate date = new MessageDate(2018, 5, 20, 11, 50, 0);
        String hours = (date.getHour() < 10) ? "0" + date.getHour() : "" + date.getHour();
        String minutes = (date.getMinute() < 10) ? "0" + date.getMinute() : "" + date.getMinute();
        String year = date.getYear() + "";
        String month = date.getMonth() + "";
        String day = date.getDay() + "";

        final String expected = MessageUtil.formattedTime(date, Calendar.getInstance());
        final String actual = String.format("%s:%s %s.%s.%s", hours, minutes, day, month, year);
        assertEquals(expected, actual);
        passed("ifNotTodayDate");
    }

    @After
    public void after() {
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
