package trickyquestion.messenger.screen.main.container.implementation;

import android.os.Bundle;
import android.view.KeyEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter;
import trickyquestion.messenger.screen.main.container.interfaces.IMainView;
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter;
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter;
import trickyquestion.messenger.ui.abstraction.mvp.activity.MvpPresenter;
import trickyquestion.messenger.util.AnimatorResource;

public class MainPresenter extends MvpPresenter<MainActivity, BaseRouter> implements IMainPresenter {
    private final IMainView view = getView();
    private final BaseRouter router = getRouter();
    private final EventManager eventManager = new EventManager(this);

    public MainPresenter(@NotNull MainActivity view, @NotNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        eventManager.subscribe();
        view.showContent();
    }

    @Override
    public void onDestroy() { eventManager.unsubscribe(); }

    @Override
    public void onFabClick() { router.openScreen(BaseRouter.Screen.ADD_FRIEND); }

    @Override
    public void onSettingsClick() {
        router.openScreen(BaseRouter.Screen.SETTINGS,
                AnimatorResource.with(R.anim.translate_top_side, R.anim.alpha_to_zero));
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU: router.openScreen(BaseRouter.Screen.SETTINGS); break;
        }
    }

    @Override
    public void changeTheme() { view.refreshTheme(); }

    @Override
    public void onAccountClick() {
        view.displayAccountPopup(true);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) view.displayFab(true);
        else view.displayFab(false);
    }
}
