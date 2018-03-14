package trickyquestion.messenger.screen.main.tabs.messages.buisness;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeMessageDbEvent;

public class EventManager extends BaseEventManager {
    private final IMessagesPresenter presenter;

    public EventManager(IMessagesPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(ChangeMessageDbEvent event){
        presenter.updateMessages();
    }

    public void onEvent(ChangeFriendDbEvent event){
        presenter.clearMessagesDeletedFriend();
    }

}
