package trickyquestion.messenger.chat_screen.model;

import android.support.annotation.NonNull;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ChatMessage implements RealmModel {

    private String table;
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

    public void setTable(@NonNull final String table) {
        this.table = table;
    }

    public String getUserTableName() {
        return table;
    }
}
