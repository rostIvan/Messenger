package trickyquestion.messenger.screen.tabs.messages.data;

import java.util.Calendar;

public class MessageUtil {
    public static String getTime(Message message) {
        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) == getYear(message)
                && calendar.get(Calendar.MONTH) == getMonth(message)
                && calendar.get(Calendar.DAY_OF_MONTH) == getDayOfMonth(message)) {
            return getHoursStr(message) + ":" + getMinutesStr(message);
        }
        return String.format("%s:%s %s.%s.%s",
                getHoursStr(message), getMinutesStr(message), getDayOfMonth(message), getMonth(message), getYear(message));
    }

    private static String getMinutesStr(Message message) {
        final int minutes = getMinutes(message, Calendar.MINUTE);
        if (minutes < 10) return "0" + minutes;
        return "" + minutes;
    }

    private static String getHoursStr(Message message) {
        final int hours = getHours(message);
        if (hours < 10) return "0" + hours;
        return "" + hours;
    }

    private static int getYear(Message message) {
        return message.getCalendar().get(Calendar.YEAR);
    }

    private static int getMonth(Message message) {
        return message.getCalendar().get(Calendar.MONTH);
    }

    private static int getDayOfMonth(Message message) {
        return message.getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    private static int getHours(Message message) {
        return message.getCalendar().get(Calendar.HOUR);
    }

    private static int getMinutes(Message message, int minute) {
        return message.getCalendar().get(minute);
    }
}
