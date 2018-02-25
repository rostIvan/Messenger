package trickyquestion.messenger.screen.add_friend.buisness;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public interface IAddFriendInteractor {
    void addToFriends(IUser user);
    List<IUser> getUsers();
}
