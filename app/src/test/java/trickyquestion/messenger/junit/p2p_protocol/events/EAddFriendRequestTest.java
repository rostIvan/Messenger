package trickyquestion.messenger.junit.p2p_protocol.events;

import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.events.EAddFriendRequest;
import trickyquestion.messenger.p2p_protocol.objects.OUser;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 04.04.2018.
 */

public class EAddFriendRequestTest {
    @Test
    public void EAddFriendRejectedCorrect() {
        OUser user = new OUser (UUID.randomUUID(),"name","1.1.1.1", Calendar.getInstance().getTime());
        EAddFriendRequest obj = new EAddFriendRequest(user);
        assertTrue(obj.getFrom().equals(user));
    }
}

