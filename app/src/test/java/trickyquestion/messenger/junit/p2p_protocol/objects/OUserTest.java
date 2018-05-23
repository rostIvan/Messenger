package trickyquestion.messenger.junit.p2p_protocol.objects;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.objects.OUser;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets on 04.04.2018.
 */

public class OUserTest {

    @Test
    public void OUser() {
        UUID id = UUID.randomUUID();
        Date time = Calendar.getInstance().getTime();
        OUser user = new OUser(id, "name", "1.1.1.1", time);
        assertTrue(user.getId().equals(id));
        assertTrue(user.getName().equals("name"));
        assertTrue(user.getNetworkAddress().equals("1.1.1.1"));
        assertTrue(user.getTTL().equals(time));

        Date newTime = Calendar.getInstance().getTime();
        user.setName("anna");
        user.setNetworkAddress("1.2.3.4");
        user.setTTL(newTime);

        assertTrue(user.getName().equals("anna"));
        assertTrue(user.getNetworkAddress().equals("1.2.3.4"));
        assertTrue(user.getTTL().equals(newTime));

        OUser secondUser = new OUser(UUID.randomUUID(), "bob", "1.1.1.2", time);
        assertTrue(user.equals(user));
        assertFalse(user.equals(secondUser));

        assertTrue(user.equalUserName(user));
        assertFalse(user.equalUserName(secondUser));


    }
}