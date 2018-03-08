package trickyquestion.messenger.screen.tabs.messages.data;

import java.util.Calendar;

import trickyquestion.messenger.screen.tabs.friends.data.Friend;

public class Message {
    private String text;
    private Friend friend;
    private Calendar calendar;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Friend friend, Calendar calendar) {
        this.text = text;
        this.friend = friend;
        this.calendar = calendar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
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
        return String.format("Message[text=%s, friend=%s, time=%s]", text, friend.getName(), time);
    }
}
