package trickyquestion.messenger.chat_screen.model;

import android.support.annotation.NonNull;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ChatMessage implements RealmModel {

    private String nameFriend;
    private String idFriend;
    private String text;
    private String time;
    private boolean my;

    public ChatMessage() {}

    public void setText(@NonNull final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setIdFriend(String idFriend) {
        this.idFriend = idFriend;
    }

    public String getIdFriend() {
        return idFriend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public boolean isMy() {
        return my;
    }

    public void setMeOwner(boolean iOwner) {
        this.my = iOwner;
    }

    public void setNameFriend(@NonNull final String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public String getNameFriend() {
        return nameFriend;
    }
}
