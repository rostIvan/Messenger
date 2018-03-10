package trickyquestion.messenger.screen.tabs.chat.data;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;

@RealmClass
public class ChatMessage implements RealmModel {
    @Required
    private String text;
    private String friendId;
    private boolean mine;
    private MessageDate date;

    public ChatMessage() {}

    public ChatMessage(String text) {
        this.text = text;
    }

    public ChatMessage(String text, UUID friendId, boolean mine, MessageDate date) {
        this.text = text;
        this.friendId = friendId.toString();
        this.mine = mine;
        this.date = date;
    }

    public ChatMessage(String text, UUID friendId, Sender sender, MessageDate date) {
        this.text = text;
        this.friendId = friendId.toString();
        this.mine = (sender == Sender.I);
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getFriendId() {
        return UUID.fromString(friendId);
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId.toString();
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public MessageDate getDate() {
        return date;
    }

    public void setDate(MessageDate date) {
        this.date = date;
    }

    public enum Sender { I, FRIEND }
}
