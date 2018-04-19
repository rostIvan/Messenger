package trickyquestion.messenger.screen.chat.buisness;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.events.EReceivedMsg;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.screen.chat.ui.IChatPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeMessageDbEvent;

public class ChatEventManager extends BaseEventManager {

    private final IChatPresenter presenter;

    public ChatEventManager(IChatPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(EReceivedMsg event) {
        final IFriend from = event.getFrom();
        final String message = event.getMsg();
        presenter.onReceiveMessage(message, from);
    }

    public void onEvent(ChangeMessageDbEvent event) {
        presenter.updateMessages();
    }

    public void onEvent(ChangeFriendDbEvent event) {
        presenter.onFriendChange();
    }

    public void onEvent(ENetworkStateChanged event) {
        final NetworkState newNetworkState = event.getNewNetworkState();
        presenter.onChangeNetwork(newNetworkState);
    }

}
