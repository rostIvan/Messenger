package trickyquestion.messenger.screen.main.container.implementation;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;

public class MainEventManager extends BaseEventManager {
    private final IMainPresenter presenter;

    public MainEventManager(IMainPresenter presenter) {
        this.presenter = presenter;
    }

    public void onEvent(ChangeThemeEvent event) { presenter.changeTheme(); }
}
