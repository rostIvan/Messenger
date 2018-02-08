package trickyquestion.messenger.screen.settings.presenter;

import android.content.Context;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import trickyquestion.messenger.screen.settings.view.ISettingView;

public interface ISettingPresenter {
    View.OnClickListener onBackPressed();
    List<ParentObject> getParents();
    ISettingView getView();
    void onCreate();

    void onChangeNameItemClick();
    void onChangePassItemClick();
    void onAskPassItemClick(boolean askPass);
    void onLogOutItemClick();

    void restartApp(Context context);

    String  getUserName();
    String  getUserId();

    void setNewLogin(String login);
    void setNewPassword(String previousPassword, String newPassword);
    void setThemePrimaryColor(int res);
    void setThemeSecondaryColor(int res);

    void setThemePrimaryColor(String colorHex);

    void setThemeSecondaryColor(String colorHex);

    void onPickColorClick();

    void onShowNotificationItemClick(boolean show);
}
