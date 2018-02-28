package trickyquestion.messenger.screen.add_friend.buisness;

import java.util.List;

import trickyquestion.messenger.buisness.DataProviderInteractor;
import trickyquestion.messenger.buisness.IDataProvider;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.model.Friend;
import trickyquestion.messenger.screen.main.tabs.friends.repository.FriendsRepository;

public class AddFriendInteractor implements IAddFriendInteractor {
    private final IDataProvider dataProvider = new DataProviderInteractor();

    @Override
    public List<IUser> getUsers() {
        final List<IUser> fromNetwork = dataProvider.getUsersFromNetwork();
        final List<Friend> friends = FriendsRepository.getFriends();
        return getUsersWithoutMyFriends(fromNetwork, friends);
    }

    @Override
    public void addToFriends(IUser user) {
        final Friend friend = new Friend(user.getName(), user.getID(), Friend.Status.ONLINE);
        FriendsRepository.addFriend(friend);
    }

    private List<IUser> getUsersWithoutMyFriends(List<IUser> fromNetwork, List<Friend> friends) {
        if (friends.isEmpty()) return fromNetwork;
        for (int i = 0; i < fromNetwork.size(); i++) {
            final IUser userFromNetwork = fromNetwork.get(i);
            final Friend friendFromDb = friends.get(i);
            if (userFromNetwork.getID().equals(friendFromDb.getId()))
                fromNetwork.remove(i);
        }
        return fromNetwork;
    }
}
