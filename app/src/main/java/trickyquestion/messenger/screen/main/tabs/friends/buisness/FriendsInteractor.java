package trickyquestion.messenger.screen.main.tabs.friends.buisness;

import java.util.List;

import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.util.java.maping.Filter;

public class FriendsInteractor implements IFriendsInteractor {

    private final FriendRepository repository;

    public FriendsInteractor(FriendRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Friend> getFriends() {
        return repository.findAll();
    }

    @Override
    public List<Friend> getFriends(String query) {
        final List<Friend> friends = getFriends();
        return Filter.friend(friends, query);
    }

    @Override
    public void deleteFriend(Friend friend) {
        repository.delete(friend);
    }

    @Override
    public void updateFriendStatus(IUser user, boolean online) {
        final List<Friend> friends = repository.findAll();
        for (Friend friend : friends) {
            if (user.getId().equals(friend.getId()))
                repository.updateFriendStatus(friend, online);
        }
    }
}
