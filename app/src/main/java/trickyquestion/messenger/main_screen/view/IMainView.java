package trickyquestion.messenger.main_screen.view;

import android.content.Context;

public interface IMainView {
    void customizeToolbar();

    void setFabBehavior();
    void hideFab();
    void showFab();

    boolean isFabShow();

    void goBack();
    void showTabsWithContent();
    void setPagerAnimation();
    void showDialogMenu();
    boolean isDialogShow();
    boolean isSearchViewIconified();
    void setSearchViewIconified(final boolean iconified);

    void startAddFriendActivity();

    //for simple test
    void showToast(final String message);
    void finish();
    void closeKeyboard();

    void showAccountPopup();

    void closeAccountPopup();

    boolean isAccountPopupShowing();

    Context getContext();
}
