package trickyquestion.messenger.util.type_cast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.p2p_protocol.P2PFriend;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.P2PProtocolService;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class TypeCasting {

    public static List<IFriend> castToIFriendList(final List<Friend> friends) {
        final List<IFriend> friendList = new ArrayList<>();
        final List<IUser> users = P2PProtocolConnector.ProtocolInterface().getUsers();
        boolean ipFound;
        for (Friend friend : friends) {
            ipFound = false;
            for(IUser user : users) {
                if (user.getID().equals(friend.getId())) {
                    friendList.add(new P2PFriend(friend.getName(), UUID.fromString(friend.getId()), user.getNetworkAddress(), friend.getEncKey()));
                    ipFound = true;
                    break;
                }
            }
            if(!ipFound) friendList.add(new P2PFriend(friend.getName(), UUID.fromString(friend.getId()), null, friend.getEncKey()));
        }
        return friendList;
    }

}
