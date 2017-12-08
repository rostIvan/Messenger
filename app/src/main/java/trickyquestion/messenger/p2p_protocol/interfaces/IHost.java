package trickyquestion.messenger.p2p_protocol.interfaces;

import java.util.UUID;

/**
 * Created by Zen on 11.10.2017.
 */

public interface IHost {
    UUID getID();
    String getName();

    byte[] getSelfEncKey();

    void setName(String new_name);
    void reCreate(UUID id, String name);
}
