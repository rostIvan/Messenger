package trickyquestion.messenger.screen.main.tabs.messages.data;

import java.util.Calendar;

import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;

public class MessageUtil {

    private MessageUtil() {}

    public static String getTime(Message message) {
        return formattedTime(message.getDate(), Calendar.getInstance());
    }
    public static String getTime(ChatMessage message) {
        return formattedTime(message.getDate(), Calendar.getInstance());
    }

    public static String formattedTime(MessageDate message, Calendar calendar) {
        if (calendar.get(Calendar.YEAR) == getYear(message)
                && calendar.get(Calendar.MONTH) == getMonth(message)
                && calendar.get(Calendar.DAY_OF_MONTH) == getDayOfMonth(message)) {
            return getHoursStr(message) + ":" + getMinutesStr(message) + ":" + getSecondsStr(message);
        }
        return String.format("%s:%s %s.%s.%s",
                getHoursStr(message),
                getMinutesStr(message),
                getDayOfMonth(message),
                getMonth(message),
                getYear(message));
    }

    private static String getSecondsStr(MessageDate messageDate) {
        return formattedTimeValue(messageDate.getSecond());
    }
    private static String getMinutesStr(MessageDate messageDate) {
        return formattedTimeValue(messageDate.getMinute());
    }
    private static String getHoursStr(MessageDate messageDate) {
        return formattedTimeValue(messageDate.getHour());
    }
    private static int getYear(MessageDate messageDate) {
        return messageDate.getYear();
    }
    private static int getMonth(MessageDate messageDate) {
        return messageDate.getMonth();
    }
    private static int getDayOfMonth(MessageDate messageDate) {
        return messageDate.getDay();
    }

    public static String formattedTimeValue(int value) {
        return value < 10 ? "0" + value : "" + value;
    }
}
