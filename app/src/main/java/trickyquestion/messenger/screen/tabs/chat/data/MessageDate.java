package trickyquestion.messenger.screen.tabs.chat.data;

import java.util.Calendar;

import io.realm.RealmObject;

public class MessageDate extends RealmObject {
    private int day;
    private int month;
    private int year;

    private int second;
    private int minute;
    private int hour;

    public MessageDate() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public static MessageDate getCurrent() {
        final Calendar calendar = Calendar.getInstance();
        final MessageDate messageDate = new MessageDate();
        messageDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        messageDate.setMonth(calendar.get(Calendar.MONTH));
        messageDate.setYear(calendar.get(Calendar.YEAR));
        messageDate.setSecond(calendar.get(Calendar.SECOND));
        messageDate.setMinute(calendar.get(Calendar.MINUTE));
        messageDate.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        return messageDate;
    }
}
