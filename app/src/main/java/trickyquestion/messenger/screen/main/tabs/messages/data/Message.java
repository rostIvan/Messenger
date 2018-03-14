package trickyquestion.messenger.screen.main.tabs.messages.data;

import trickyquestion.messenger.screen.chat.data.MessageDate;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public class Message {
    private String text;
    private Friend friend;
    private MessageDate date;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Friend friend, MessageDate date) {
        this.text = text;
        this.friend = friend;
        this.date = date;
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

    public MessageDate getDate() {
        return date;
    }

    public void setDate(MessageDate date) {
        this.date = date;
    }
}
