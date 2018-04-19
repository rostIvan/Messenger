package trickyquestion.messenger.junit.util.java.maping;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.util.java.maping.FriendFilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nadiia Bogoslavets and Basil Polych on 24.02.2018.
 */

public class FriendFilterTest {

    @Test
    public void isFriendFilterCorrect() {
        List<Friend> friends = new ArrayList<>();
        friends.add(new Friend("Anna", UUID.randomUUID(),false));
        friends.add(new Friend("Rin", UUID.randomUUID(),false));
        String valid = "Ann";
        String incorrect = "dog";
        assertFalse("Friend filter not found valid str", FriendFilter.filter(friends, valid).isEmpty());
        assertTrue("Friend filter not work with invalid str",FriendFilter.filter(friends, incorrect).isEmpty());
    }

}
