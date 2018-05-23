package trickyquestion.messenger.screen.add_friend.data;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class UserUtil {
    public static String getName(IUser user) { return user.getName(); }
    public static String getId(IUser user) { return user.getId().toString().substring(0,25).concat(" ... "); }
}