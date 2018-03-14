package trickyquestion.messenger.screen.main.tabs.friends.ui;

import java.util.List;

import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;

public interface IFriendsPresenter {
    void updateFriends();
    void onFriendItemClick(Friend model, FriendViewHolder holder, List<Friend> items);
    void onFriendRemoveClick(Friend model);
    void onFriendImageClick(Friend model);
    void onQueryTextChanged(String query);
    void onNetworkStateChanged(NetworkState state);
    void onChangeFriendState(IUser user, boolean online);
}
