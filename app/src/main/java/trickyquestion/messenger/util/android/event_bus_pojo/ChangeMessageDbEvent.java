package trickyquestion.messenger.util.android.event_bus_pojo;

public class ChangeMessageDbEvent {
    private final String message;

    public ChangeMessageDbEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
