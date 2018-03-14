package trickyquestion.messenger.screen.main.tabs.friends.data;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class Friend implements RealmModel {
    @Required
    private String id;
    private String name;
    private boolean online;
    private byte[] encryptionKey;

    public Friend() {
    }

    public Friend(final String name, final UUID id, final Status status, final byte[] encryptionKey) {
        this.name = name;
        this.id = id.toString();
        this.online = (status == Status.ONLINE);
        this.encryptionKey = encryptionKey;
    }

    public Friend(final String name, final UUID id) {
        this.name = name;
        this.id = id.toString();
    }

    public Friend(final String name, final UUID id, final Status status) {
        this.name = name;
        this.id = id.toString();
        this.online = (status == Status.ONLINE);
    }

    public Friend(final String name, final UUID id, final boolean online) {
        this.name = name;
        this.id = id.toString();
        this.online = online;
    }

    public Friend(final String name, final UUID id, final boolean online, final byte[] encryptionKey) {
        this.name = name;
        this.id = id.toString();
        this.online = online;
        this.encryptionKey = encryptionKey;
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

    public void setOnline(Status status) {
        this.online = (status == Status.ONLINE);
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

    public byte[] getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(byte[] encryptionKey) {
        this.encryptionKey = encryptionKey;
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

    public enum Status { ONLINE, OFFLINE }
}
