package trickyquestion.messenger.util.type_cast;

import java.util.UUID;

import trickyquestion.messenger.add_friend_screen.model.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class TypeCasting {
    public static IUser castToUser(final IFriend friend) {
        final IUser user = new IUser() {
            @Override
            public UUID getID() {
                return friend.getId();
            }
            @Override
            public String getName() {
                return friend.getName();
            }
            @Override
            public String getNetworkAddress() {return null; }
            @Override
            public void setName(String newName) {}
            @Override
            public void setNetworkAddress(String newIP) {}
        };
        return user;
    }
}
