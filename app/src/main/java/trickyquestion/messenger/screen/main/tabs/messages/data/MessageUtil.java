package trickyquestion.messenger.screen.main.tabs.messages.data;

import java.util.Calendar;

import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.screen.chat.data.MessageDate;

public class MessageUtil {

    public static String getTime(Message message) {
        return getTimeStr(message.getDate());
    }

    public static String getTime(ChatMessage message) {
        return getTimeStr(message.getDate());
    }

    private static String getTimeStr(MessageDate message) {
        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) == getYear(message)
                && calendar.get(Calendar.MONTH) == getMonth(message)
                && calendar.get(Calendar.DAY_OF_MONTH) == getDayOfMonth(message)) {
            return getHoursStr(message) + ":" + getMinutesStr(message) + ":" + getSecondsStr(message);
        }
        return String.format("%s:%s %s.%s.%s",
                getHoursStr(message), getMinutesStr(message), getDayOfMonth(message), getMonth(message), getYear(message));
    }

    private static String getSecondsStr(MessageDate messageDate) {
        final int seconds = getSeconds(messageDate);
        if (seconds < 10) return "0" + seconds;
        return "" + seconds;
    }

    private static String getMinutesStr(MessageDate messageDate) {
        final int minutes = getMinutes(messageDate);
        if (minutes < 10) return "0" + minutes;
        return "" + minutes;
    }

    private static String getHoursStr(MessageDate messageDate) {
        final int hours = getHours(messageDate);
        if (hours < 10) return "0" + hours;
        return "" + hours;
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

    private static int getHours(MessageDate messageDate) {
        return messageDate.getHour();
    }

    private static int getMinutes(MessageDate messageDate) {
        return messageDate.getMinute();
    }

    private static int getSeconds(MessageDate messageDate) {
        return messageDate.getSecond();
    }
}
