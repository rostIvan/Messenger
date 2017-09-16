package trickyquestion.messenger.main_screen.view;

import android.content.Context;

public interface IMainView {
    void customizeToolbar();
    void goBack();
    void showTabsWithContent();
    void setPagerAnimation();
    void showDialogMenu();
    boolean isDialogShow();
    boolean isSearchViewIconified();
    void setSearchViewIconified(final boolean iconified);
    //for simple test
    void showToast(final String message);

    void startLoginActivity();
    void finish();

    Context getContext();
}
