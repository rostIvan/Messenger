package trickyquestion.messenger.screen.main.tabs.friends.buisness;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public class FriendsEventManager extends BaseEventManager {
    private final IFriendsPresenter presenter;

    public FriendsEventManager(IFriendsPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(ChangeFriendDbEvent event) {
        presenter.updateFriends();
    }

    public void onEvent(ENetworkStateChanged event) {
        final NetworkState networkState = event.getNewNetworkState();
        presenter.onNetworkStateChanged(networkState);
    }

    public void onEvent(ChangeUserList event) {
        final IUser user = event.getUser();
        final boolean online = event.exist();
        presenter.onChangeFriendState(user, online);
    }

}
