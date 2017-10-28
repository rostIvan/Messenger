package trickyquestion.messenger.util.event_bus_pojo;

public class SendMessageEvent {
    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
