package trickyquestion.messenger.screen.main.container.interfaces;

import android.view.KeyEvent;

import trickyquestion.messenger.ui.interfaces.BasePresenter;

public interface IMainPresenter extends BasePresenter {
    void onFabClick();
    void onAccountClick();
    void onPageSelected(int position);
    void onSettingsClick();
    void onKeyDown(int keyCode, KeyEvent event);
    void changeTheme();
}
