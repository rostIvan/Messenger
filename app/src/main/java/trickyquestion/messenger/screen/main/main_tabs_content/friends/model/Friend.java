package trickyquestion.messenger.screen.main.main_tabs_content.friends.model;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class Friend implements RealmModel {

    private String name;
    @Required
    private String id;
    private boolean online;

    public Friend() {
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

    public void setOnline(boolean online) {
        this.online = online;
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Friend) {
            Friend friend = (Friend) obj;
            return friend.id.equals(id) && friend.name.equals(name) && friend.online == online;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Friend[id=%s, name=%s, online=%b]", id, name, online);
    }
}
