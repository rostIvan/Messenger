package trickyquestion.messenger.Protocol.Interfaces;

import java.util.UUID;

/**
 * Created by Zen on 23.09.2017.
 */

public interface IUser {
    UUID getID();
    String getName();
    String get_network_address();
}
