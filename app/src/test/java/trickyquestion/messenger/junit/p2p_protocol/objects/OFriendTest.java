package trickyquestion.messenger.junit.p2p_protocol.objects;

import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.objects.OFriend;
import trickyquestion.messenger.p2p_protocol.objects.OUser;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 04.04.2018.
 */

public class OFriendTest {
    @Test
    public void OFriend(){
        UUID id = UUID.randomUUID();
        OFriend friend =  new OFriend("test",id ,"1.1.1.1");
        assertTrue(friend.getID().equals(id));
        assertTrue(friend.getName().equals("test"));
        assertTrue(friend.getNetworkAddress().equals("1.1.1.1"));

        OUser friendForCorrectUpdate =  new OUser(id,"name","1.1.1.2",
                Calendar.getInstance().getTime());
        OUser friendForIncorrectUpdate =  new OUser(UUID.randomUUID(),"other","1.1.1.3",
                Calendar.getInstance().getTime());
        friend.updateData(friendForCorrectUpdate);
        assertTrue(
                friend.getName().equals(friendForCorrectUpdate.getName())&&
                        friend.getNetworkAddress().equals(friendForCorrectUpdate.getNetworkAddress())
        );
        friend.updateData(friendForIncorrectUpdate);
        assertFalse(
                friend.getName().equals(friendForIncorrectUpdate.getName())&&
                        friend.getNetworkAddress().equals(friendForIncorrectUpdate.getNetworkAddress())
        );
    }
}