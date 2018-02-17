package trickyquestion.messenger.util.java.maping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;
import trickyquestion.messenger.p2p_protocol.objects.P2PFriend;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
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
                    friendList.add(new P2PFriend(friend.getName(), friend.getId(), user.getNetworkAddress()));
                    ipFound = true;
                    break;
                }
            }
            if(!ipFound) friendList.add(new P2PFriend(friend.getName(), friend.getId(), null));
        }
        return friendList;
    }

}
