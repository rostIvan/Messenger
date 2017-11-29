package trickyquestion.messenger.util.event_bus_pojo;

public class ChangeMessageDataBaseEvent {
    private final String message;

    public ChangeMessageDataBaseEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
