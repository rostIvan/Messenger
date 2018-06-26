package trickyquestion.messenger.screen.main.container.implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import trickyquestion.messenger.R;
import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter;
import trickyquestion.messenger.screen.main.container.interfaces.IMainView;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.activity.MvpPresenter;
import trickyquestion.messenger.ui.util.AnimatorResource;

public class MainPresenter extends MvpPresenter<IMainView, BaseRouter> implements IMainPresenter {
    private BaseEventManager eventManager;

    public MainPresenter(@NonNull IMainView view,
                         @NonNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void attach(@NonNull BaseEventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        eventManager.subscribe();
        getView().showContent();
    }

    @Override
    public void onDestroy() { eventManager.unsubscribe(); }

    @Override
    public void onFabClick() { getRouter().openScreen(BaseRouter.Screen.ADD_FRIEND); }

    @Override
    public void onSettingsClick() {
        getRouter().openScreen(BaseRouter.Screen.SETTINGS,
                AnimatorResource.with(R.anim.translate_top_side, R.anim.alpha_to_zero));
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU: getRouter().openScreen(BaseRouter.Screen.SETTINGS); break;
        }
    }

    @Override
    public void changeTheme() { getView().refreshTheme(); }

    @Override
    public void onAccountClick() {
        getView().displayAccountPopup(true);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) getView().displayFab(true);
        else getView().displayFab(false);
    }
}
