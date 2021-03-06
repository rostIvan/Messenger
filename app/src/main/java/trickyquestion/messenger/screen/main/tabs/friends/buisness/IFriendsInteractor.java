package trickyquestion.messenger.screen.main.tabs.friends.buisness;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public interface IFriendsInteractor {
    List<Friend> getFriends();
    List<Friend> getFriends(String query);
    void deleteFriend(Friend friend);
    void updateFriendStatus(IUser user, boolean online);
}
