package trickyquestion.messenger.main_screen.main_tabs_content.model;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message {

    private String message;
    private String nameSender;
    private CircleImageView imageSender;
    private String time;
    private boolean wasRead;

    public Message(final String message,
                   final String nameSender,
                   final CircleImageView imageSender,
                   final String time,
                   final boolean wasRead) {
        this.message = message;
        this.nameSender = nameSender;
        this.imageSender = imageSender;
        this.time = time;
        this.wasRead = wasRead;
    }

    public String getLastMessage() {
        return message;
    }

    public String getNameSender() {
        return nameSender;
    }

    public CircleImageView getImageSender() {
        return imageSender;
    }

    public String getTime() {
        return time;
    }

    public boolean wasRead() {
        return wasRead;
    }
}
