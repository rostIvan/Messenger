package trickyquestion.messenger.screen.main.container.interfaces;

import android.view.KeyEvent;

public interface IMainPresenter {
    void onFabClick();
    void onAccountClick();
    void onPageSelected(int position);
    void onSettingsClick();
    void onKeyDown(int keyCode, KeyEvent event);
    void changeTheme();
}
