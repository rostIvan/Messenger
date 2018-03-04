package trickyquestion.messenger.util.android.event_bus_pojo;

public class ChangeFriendDbEvent {
    private final String message;

    public ChangeFriendDbEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}