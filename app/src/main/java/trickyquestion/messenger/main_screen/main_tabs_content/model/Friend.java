package trickyquestion.messenger.main_screen.main_tabs_content.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
public class Friend implements RealmModel {

    private String name;
    @Ignore
    private UUID id;
    @Ignore
    private ImageView image;
    private boolean online;

    public Friend() {
    }

    public Friend(final String name, final UUID id, final ImageView image, final boolean online) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public static List<Friend> getFriends(int size) {
        final List<Friend> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Friend friend = new Friend("Some name: " + (i+1), UUID.randomUUID(), null, i % 2 == 0);
            list.add(friend);
        }
        return list;
    }

    public UUID getId() {
        return id;
    }

    public ImageView getImage() {
        return image;
    }

    public boolean isOnline() {
        return online;
    }

    public static Friend find(final Friend friend, final List<Friend> friendList) {
        for (Friend f : friendList) {
            if (f.getId().equals(friend.getId()))
                return f;
        }
        return null;
    }
}
