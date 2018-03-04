package trickyquestion.messenger.screen.tabs.friends.buisness;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.repository.FriendsRepository;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.util.java.maping.FriendFilter;

public class FriendInteractor implements IFriendInteractor {
    @Override
    public List<Friend> getFriends() {
        return FriendsRepository.getFriends();
    }

    @Override
    public List<Friend> getFriends(String query) {
        final List<Friend> friends = FriendsRepository.getFriends();
        return FriendFilter.filter(friends, query);
    }

    @Override
    public void deleteFriend(Friend friend) {
        FriendsRepository.deleteFriend(friend);
    }

    @Override
    public void updateFriendStatus(IUser user, boolean online) {
        final List<Friend> friends = FriendsRepository.getFriends();
        for (Friend friend : friends) {
            if (user.getID().equals(friend.getId()))
                FriendsRepository.changeFriendOnlineStatus(friend, online);
        }
    }
}
