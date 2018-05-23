package trickyquestion.messenger.p2p_protocol.interfaces;

import java.util.UUID;

/**
 * Created by Zen on 11.10.2017.
 */

public interface IUser {
    UUID getId();
    String getName();
    String getNetworkAddress();

    void setName(String newName);
    void setNetworkAddress(String newIP);
}
