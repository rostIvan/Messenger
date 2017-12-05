package trickyquestion.messenger.util.type_cast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.add_friend_screen.model.Friend;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class TypeCasting {
    public static IUser castToUser(final trickyquestion.messenger.add_friend_screen.model.IFriend friend) {
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
    // put argument like this: FriendRepository.getFriends()
    public List<IFriend> castToIFriendList(final List<Friend> friends) {
        final List<IFriend> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            friendList.add(new IFriend() {
                @Override
                public UUID getID() {return friend.getId();}
                @Override
                public String getName() {return friend.getName();}
                @Override
                public byte[] encKey() {return new byte[0];}
                @Override
                public String getNetworkAddress() {return null;}
                @Override
                public void updateData(IUser user) {}
            });
        }
        return friendList;
    }
}
