package trickyquestion.messenger.settings_screen.view;

import android.content.Context;

public interface ISettingView {
    void goBack();
    Context getContext();

    void showChangeLoginDialog();
    void showChangePasswordDialog();

    void setNewLoginText(String loginText);

    void showToast(String text);
}
