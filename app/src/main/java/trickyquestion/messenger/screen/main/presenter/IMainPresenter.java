package trickyquestion.messenger.screen.main.presenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

public interface IMainPresenter {
    void onCreate();
    void onSaveInstanceState(Bundle outState);
    void onRestoreInstanceState(Bundle savedInstanceState);
    void onFinish();
    boolean onKeyDown(int keyCode, KeyEvent event);
    void onBackPressed();
    View.OnClickListener onNavigationButtonPressed();
    MenuItem.OnMenuItemClickListener onSettingClick();
    MenuItem.OnMenuItemClickListener onAccountMenuItemClick();
    ViewPager.OnPageChangeListener onPageChangeListener();

    View.OnClickListener onFabClick();
}
