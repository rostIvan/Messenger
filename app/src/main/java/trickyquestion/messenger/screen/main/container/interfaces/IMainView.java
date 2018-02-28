package trickyquestion.messenger.screen.main.container.interfaces;

import android.support.annotation.NonNull;

import trickyquestion.messenger.ui.abstraction.interfaces.BaseView;

public interface IMainView extends BaseView {
    void showContent();
    void displayAccountPopup(boolean display);
    void displayFab(boolean display);
    void refreshTheme();
    default void showToast(@NonNull CharSequence text) {}
    default void onUiThread(@NonNull Runnable runnable) {}
}