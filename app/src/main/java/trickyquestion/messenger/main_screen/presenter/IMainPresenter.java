package trickyquestion.messenger.main_screen.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

public interface IMainPresenter {
    void onCreate();
    void onResume();
    View.OnClickListener onNavigationButtonPressed();
    boolean onKeyDown(int keyCode, KeyEvent event);
    void onSaveInstanceState(Bundle outState);
    MenuItem.OnMenuItemClickListener onSettingClick();
    MenuItem.OnMenuItemClickListener onAccountMenuItemClick();
    void onActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_AUTH);
}
