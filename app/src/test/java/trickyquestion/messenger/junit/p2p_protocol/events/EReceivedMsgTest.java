package trickyquestion.messenger.junit.p2p_protocol.events;

import org.junit.Test;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.events.EReceivedMsg;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.objects.OFriend;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 04.04.2018.
 */

public class EReceivedMsgTest {
    @Test
    public void EReceivedMsgCorrect(){
        IFriend friend =  new OFriend("test", UUID.randomUUID(),"1.1.1.1");
        EReceivedMsg obj = new EReceivedMsg("test", friend);
        assertTrue(obj.getFrom().equals(friend));
        assertTrue(obj.getMsg().equals("test"));
    }
}
