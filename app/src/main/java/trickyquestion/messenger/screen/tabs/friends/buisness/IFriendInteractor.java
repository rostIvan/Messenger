package trickyquestion.messenger.screen.tabs.friends.buisness;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;

public interface IFriendInteractor {
    List<Friend> getFriends();
    List<Friend> getFriends(String query);
    void deleteFriend(Friend friend);
    void updateFriendStatus(IUser user, boolean online);
}
