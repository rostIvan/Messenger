package trickyquestion.messenger.screen.add_friend.buisness;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.screen.add_friend.ui.IAddFriendPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public class EventManager {
    private final IAddFriendPresenter presenter;

    public EventManager(IAddFriendPresenter presenter) {
        this.presenter = presenter;
    }
    
    public void subscribe() { EventBus.getDefault().register(this); }
    public void unsubscribe() { EventBus.getDefault().unregister(this); }

    public void onEvent(ChangeUserList event) { presenter.updateUsers(); }

    public void onEvent(ChangeThemeEvent event) { presenter.changeTheme(); }

    public void onEvent(final ENetworkStateChanged event) {
        switch (event.getNewNetworkState()) {
            case ACTIVE:  presenter.updateUsers(); break;
            case INACTIVE:  presenter.hideUsers(); break;
        }
    }
}
