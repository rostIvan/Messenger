package trickyquestion.messenger.screen.tabs.friends.buisness;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.screen.tabs.friends.ui.IFriendPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public class EventManager extends BaseEventManager {
    private final IFriendPresenter presenter;

    public EventManager(IFriendPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(ChangeFriendDbEvent event) {
        presenter.updateFriends();
    }

    public void onEvent(ENetworkStateChanged event) {
        presenter.onNetworkStateChanged(event);
    }

    public void onEvent(ChangeUserList event) {
        presenter.onChangeFriendState(event);
    }

}
