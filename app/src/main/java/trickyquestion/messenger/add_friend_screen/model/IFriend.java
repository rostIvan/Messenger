package trickyquestion.messenger.add_friend_screen.model;

import android.widget.ImageView;

import java.util.UUID;

public interface IFriend {
    String getName();
    void setName(String name);
    UUID getId();
    void setId(UUID id);
    ImageView getImage();
    void setImage(ImageView image);
    boolean isOnline();
    void setOnline(boolean online);
}
