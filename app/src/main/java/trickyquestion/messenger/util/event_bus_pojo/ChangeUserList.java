package trickyquestion.messenger.util.event_bus_pojo;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class ChangeUserList {
    private final String message;

    public ChangeUserList(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
