package trickyquestion.messenger.junit.screen.add_friend.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.objects.OUser;
import trickyquestion.messenger.screen.add_friend.data.User;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class UserUtilTest {

    @Test
    public void getName_shouldReturnNameUser() {
        final IUser user = new User("Ashley", UUID.randomUUID(), null);
        final String name = UserUtil.getName(user);
        assertEquals(name, "Ashley");
    }

    @Test
    public void getId_shouldReturnCompactRepresentation() {
        final UUID id = UUID.randomUUID();
        final IUser user = new User("Ashley", id, null);
        final String actualId = UserUtil.getId(user);
        assertTrue(actualId.length() <= 30);
        assertTrue(actualId.contains("..."));
        assertTrue(actualId.contains(id.toString().substring(0, 25)));
    }
}
