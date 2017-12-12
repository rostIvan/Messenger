package trickyquestion.messenger.main_screen.main_tabs_content.model;

import android.widget.ImageView;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
public class Friend implements RealmModel {

    private String name;
    private String id;
    @Ignore
    private ImageView image;
    private boolean online;

    public Friend() {
    }

    public Friend(final String name, final UUID id, final ImageView image, final boolean online) {
        this.name = name;
        this.id = id.toString();
        this.image = image;
        this.online = online;
    }
    public Friend(final String name, final UUID id, final boolean online) {
        this.name = name;
        this.id = id.toString();
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id.toString();
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String  getId() {
        return id;
    }

    public ImageView getImage() {
        return image;
    }

    public boolean isOnline() {
        return online;
    }
}
