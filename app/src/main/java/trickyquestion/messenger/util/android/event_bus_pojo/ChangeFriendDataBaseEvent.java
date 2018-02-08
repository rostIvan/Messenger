package trickyquestion.messenger.util.android.event_bus_pojo;

public class ChangeFriendDataBaseEvent {
    private final String message;

    public ChangeFriendDataBaseEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}