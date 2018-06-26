package trickyquestion.messenger.screen.main.tabs.friends.ui;

import java.util.List;

import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.interfaces.BasePresenter;

public interface IFriendsPresenter extends BasePresenter {
    void updateFriends();
    void onFriendItemClick(Friend model, List<Friend> items);
    void onFriendRemoveClick(Friend model);
    void onFriendImageClick(Friend model);
    void onQueryTextChanged(String query);
    void onNetworkStateChanged(NetworkState state);
    void onChangeFriendState(IUser user, boolean online);
}
