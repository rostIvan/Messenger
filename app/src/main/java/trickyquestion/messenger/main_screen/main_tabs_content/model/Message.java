package trickyquestion.messenger.main_screen.main_tabs_content.model;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//TODO:clean imports and list<message> must be implemented
public class Message {

    private String message;
    private String nameSender;
    private CircleImageView imageSender;
    private String time;
    private boolean my;
    private boolean wasRead;

    public Message(final String message,
                   final String nameSender,
                   final CircleImageView imageSender,
                   final String time,
                   final boolean my,
                   final boolean wasRead) {
        this.message = message;
        this.nameSender = nameSender;
        this.imageSender = imageSender;
        this.time = time;
        this.my = my;
        this.wasRead = wasRead;
    }

    public String getMessageText() {
        return message;
    }

    public static List<Message> getMessages(final int size) {
        final List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Message message = new Message(
                    "Some message: " + (i + 1), "Some name: " + (i + 1),
                    null, "12:00",
                    i % 2 == 0, i % 2 != 0
            );
            messageList.add(message);
        }
        return messageList;
    }

    public String getMessage() {
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

    public boolean isMy() {
        return my;
    }

    public boolean isWasRead() {
        return wasRead;
    }
}
