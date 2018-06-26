package trickyquestion.messenger.util.java.maping;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.buisness.DataProvider;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.objects.OFriend;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public class TypeMapping {

    private TypeMapping() {}

    public static List<IFriend> toIFriendList(@NonNull final List<Friend> friends,
                                              @NonNull final DataProvider dataProvider) {
        final List<IFriend> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            friendList.add(toIFriend(friend, dataProvider));
        }
        return friendList;
    }

    public static IFriend toIFriend(@NonNull final Friend friend,
                                    @NonNull final DataProvider dataProvider) {
        final List<IUser> users = dataProvider.getUsersFromNetwork();
        for(IUser user : users) {
            if(user.getId().equals(friend.getId()))
                return new OFriend(friend.getName(), friend.getId(), user.getNetworkAddress());
        }
        return new OFriend(friend.getName(),friend.getId(), null);
    }

}
