package trickyquestion.messenger.screen.tabs.friends.ui;

import java.util.List;

import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public interface IFriendPresenter {
    void updateFriends();
    void onFriendItemClick(Friend model, FriendViewHolder holder, List<Friend> items);
    void onFriendRemoveClick(Friend model);
    void onFriendImageClick(Friend model, FriendViewHolder holder, List<Friend> items);
    void onQueryTextChanged(String query);
    void onNetworkStateChanged(ENetworkStateChanged event);
    void onChangeFriendState(ChangeUserList event);
}
