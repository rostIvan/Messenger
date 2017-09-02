package trickyquestion.messenger.MainScreen.Presenter;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public interface IMainPresenter {
    void onCreate();
    View.OnClickListener onNavigationButtonPressed();
    boolean onKeyDown(int keyCode, KeyEvent event);
    void onSaveInstanceState(Bundle outState);
    MenuItem.OnMenuItemClickListener onSettingClick();
}
