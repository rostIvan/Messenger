package trickyquestion.messenger.Protocol.Interfaces;

import java.util.UUID;

/**
 * Created by Zen on 23.09.2017.
 */

public interface IClient extends IFriend {
    UUID getID();
    String getName();
    String get_network_address();
    String getImage();

    void setName(String new_name);
    void setNetworkAddress(String new_address);
    void recreate(UUID id, String name, String network_address);
}
