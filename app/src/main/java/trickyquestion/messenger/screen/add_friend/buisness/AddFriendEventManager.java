package trickyquestion.messenger.screen.add_friend.buisness;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.screen.add_friend.ui.IAddFriendPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public class AddFriendEventManager extends BaseEventManager {
    private final IAddFriendPresenter presenter;

    public AddFriendEventManager(IAddFriendPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(ChangeUserList event) { presenter.updateUsers(); }

    public void onEvent(ChangeThemeEvent event) { presenter.changeTheme(); }

    public void onEvent(final ENetworkStateChanged event) {
        final NetworkState state = event.getNewNetworkState();
        if (state == NetworkState.ACTIVE) presenter.updateUsers();
        else if (state == NetworkState.INACTIVE) presenter.hideUsers();
    }
}
