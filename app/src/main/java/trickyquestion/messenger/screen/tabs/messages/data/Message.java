package trickyquestion.messenger.screen.tabs.messages.data;

import java.util.Calendar;

public class Message {
    private String text;
    private String friendName;
    private Calendar calendar;

    public Message() {
    }

    public Message(String text, String friendName, Calendar calendar) {
        this.text = text;
        this.friendName = friendName;
        this.calendar = calendar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public String toString() {
    final String time = calendar.get(Calendar.DAY_OF_WEEK) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) + "  " +
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return String.format("Message[text=%s, friend=%s, time=%s]", text, friendName, time);
    }
}
