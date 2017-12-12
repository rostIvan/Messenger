package trickyquestion.messenger.p2p_protocol.interfaces;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Created by Zen on 11.10.2017.
 */

public interface IFriend {
    UUID getID();
    String getName();

    String getNetworkAddress();

    void updateData(IUser user);
}
