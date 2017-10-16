package trickyquestion.messenger.add_friend_screen.model;

import android.widget.ImageView;

import java.util.UUID;


public class Friend implements IFriend {

    private String name;
    private UUID id;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public void setImage(ImageView image) {
        this.image = image;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    @Override
    public void setOnline(boolean online) {
        this.online = online;
    }
}
