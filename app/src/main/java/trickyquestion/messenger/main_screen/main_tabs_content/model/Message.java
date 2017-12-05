package trickyquestion.messenger.main_screen.main_tabs_content.model;
import de.hdodenhof.circleimageview.CircleImageView;

public class Message {

    private String message;
    private String nameFriend;
    private String idFriend;
    private CircleImageView imageSender;
    private String time;
    private boolean wasRead;

    public Message(final String message,
                   final String nameSender,
                   final String idFriend,
                   final CircleImageView imageSender,
                   final String time,
                   final boolean wasRead) {
        this.message = message;
        this.nameFriend = nameSender;
        this.idFriend = idFriend;
        this.imageSender = imageSender;
        this.time = time;
        this.wasRead = wasRead;
    }

    public String getMessage() {
        return message;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    public CircleImageView getImageSender() {
        return imageSender;
    }

    public String getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(String idFriend) {
        this.idFriend = idFriend;
    }

    public String getTime() {
        return time;
    }

    public boolean wasRead() {
        return wasRead;
    }
}
