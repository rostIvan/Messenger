package trickyquestion.messenger.p2p_protocol.events;

import org.junit.Test;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.events.EAddFriendConfirmed;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.objects.OFriend;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Subaru on 28.03.2018.
 */

public class EAddFriendConfirmedTest {
    @Test
    public void EAddFriendConfirmedCorrect(){
        IFriend friend = new OFriend("test", UUID.randomUUID(),"1.1.1.1");
        EAddFriendConfirmed obj = new EAddFriendConfirmed(friend);
        assertTrue(obj.getFriend().equals(friend));
    }
}
