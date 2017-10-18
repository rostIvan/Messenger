package trickyquestion.messenger.p2p_protocol.interfaces;

import java.util.UUID;

/**
 * Created by Zen on 11.10.2017.
 */

public interface IClient extends IFriend {
    UUID getID();
    String getName();
    String getImage();

    void setName(String new_name);
    void recreate(UUID id, String name, String network_address);
}
