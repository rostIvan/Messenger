package trickyquestion.messenger.MainScreen.View;

public interface IMainView {
    void customizeToolbar();
    void goBack();
    void showTabsWithContent();
    void setPagerAnimation();
    void showDialogMenu();
    boolean isDialogShow();
    //for simple test
    void showToast(final String message);
}
