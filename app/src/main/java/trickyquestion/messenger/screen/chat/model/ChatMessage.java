package trickyquestion.messenger.screen.chat.model;

import android.support.annotation.NonNull;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ChatMessage implements RealmModel {

    private String nameFriend;
    private String idFriend;
    private String text;
    private String time;
    private boolean mine;

    public ChatMessage() {}

    public void setText(@NonNull final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setIdFriend(UUID idFriend) {
        this.idFriend = idFriend.toString();
    }

    public UUID getIdFriend() {
        return UUID.fromString(idFriend);
    }

    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setNameFriend(@NonNull final String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public String getNameFriend() {
        return nameFriend;
    }
}
