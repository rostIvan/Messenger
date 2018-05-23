package trickyquestion.messenger.screen.add_friend.buisness;

import java.util.List;

import trickyquestion.messenger.buisness.IDataProvider;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public class AddFriendInteractor implements IAddFriendInteractor {
    private final IDataProvider dataProvider;
    private final FriendRepository repository;

    public AddFriendInteractor(IDataProvider dataProvider, FriendRepository repository) {
        this.dataProvider = dataProvider;
        this.repository = repository;
    }

    @Override
    public List<IUser> getUsers() {
        final List<IUser> fromNetwork = dataProvider.getUsersFromNetwork();
        final List<Friend> friends = repository.findAll();
        return getUsersWithoutMyFriends(fromNetwork, friends);
    }

    @Override
    public void addToFriends(IUser user) {
        final Friend friend = new Friend(user.getName(), user.getId(), Friend.Status.ONLINE);
        repository.save(friend);
    }

    private List<IUser> getUsersWithoutMyFriends(List<IUser> fromNetwork, List<Friend> friends) {
        if (friends.isEmpty()) return fromNetwork;
        for (int i = 0; i < fromNetwork.size(); i++) {
            if (friends.size() < i) return fromNetwork;
            final IUser userFromNetwork = fromNetwork.get(i);
            final Friend friendFromDb = friends.get(i);
            if (userFromNetwork.getId().equals(friendFromDb.getId()))
                fromNetwork.remove(i);
        }
        return fromNetwork;
    }
}
