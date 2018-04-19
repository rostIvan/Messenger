package trickyquestion.messenger.junit.p2p_protocol.events;

import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.events.EAddFriendRejected;
import trickyquestion.messenger.p2p_protocol.objects.OUser;

import static junit.framework.Assert.assertTrue;


/**
 * Created by Nadiia Bogoslavets on 03.04.2018.
 */

public class EAddFriendRejectedTest {
    @Test
    public void EAddFriendRejectedCorrect() {
        OUser user = new OUser (UUID.randomUUID(),"name","1.1.1.1", Calendar.getInstance().getTime());
        EAddFriendRejected obj = new EAddFriendRejected(user);
        assertTrue(obj.getFriend().equals(user));
    }
}
