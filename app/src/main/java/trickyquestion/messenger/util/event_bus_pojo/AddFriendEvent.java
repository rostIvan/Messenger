package trickyquestion.messenger.util.event_bus_pojo;

public class AddFriendEvent {
    private final String message;

    public AddFriendEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}