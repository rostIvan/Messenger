package trickyquestion.messenger.screen.settings.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.screen.settings.expand_list.data.ExpandedListCreator;
import trickyquestion.messenger.screen.settings.view.ISettingView;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.android.preference.ThemePreference;
import trickyquestion.messenger.util.java.validation.UserInputValidator;

public class SettingPresenter implements ISettingPresenter {

    private final ISettingView view;
    private final AuthPreference authPreference;
    private final ThemePreference themePreference;
    private final ExpandedListCreator listCreator;

    public SettingPresenter(ISettingView view) {
        this.view = view;
        this.authPreference = new AuthPreference(view.getContext());
        this.themePreference = new ThemePreference(view.getContext());
        this.listCreator = new ExpandedListCreator(view.getContext());
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.customizeTheme();
        view.setUserData();
        view.customizeRecycler();
    }

    @Override
    public ISettingView getView() {
        return view;
    }

    @Override
    public View.OnClickListener onBackPressed() {
        return v -> view.goBack();
    }

    @Override
    public List<ParentObject> getParents() {
        return listCreator.getParents();
    }

    @Override
    public void onChangeNameItemClick() {
        view.showChangeLoginDialog();
    }

    @Override
    public void onChangePassItemClick() {
        view.showChangePasswordDialog();
    }

    @Override
    public void onAskPassItemClick(boolean askPass) {
        if (askPass) authPreference.setAskingPassword(true);
        else authPreference.setAskingPassword(false);
    }

    @Override
    public void onShowNotificationItemClick(boolean show) {
        if (show) authPreference.setShowNotifications(true);
        else authPreference.setShowNotifications(false);
    }

    @Override
    public void onLogOutItemClick() {
        authPreference.clearAccountData();
        authPreference.setUserAuthenticated(false);
        restartApp(view.getContext());
    }

    @Override
    public void restartApp(Context context) {
        final Intent i = context.getPackageManager()
                .getLaunchIntentForPackage( context.getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public String getUserName() {
        return authPreference.getAccountLogin();
    }

    @Override
    public String getUserId() {
        return authPreference.getAccountId().trim().substring(0, 30).concat(" ... ");
    }

    @Override
    public void setNewLogin(String login) {
        if (UserInputValidator.isLoginValid(login)) {
            view.showToast("Login was changed");
            authPreference.setAccountLogin(login);
            view.setNewLoginText(login);
        } else {
            view.showToast("Entered login isn't correct, try again");
            view.showChangeLoginDialog();
        }
    }

    @Override
    public void setNewPassword(String previousPassword, String newPassword) {
        if ( !UserInputValidator.isPreviousPasswordCorrect(previousPassword, view.getContext()) ) {
            view.showToast("Wrong entered previous password");
            view.showChangePasswordDialog();
        }
        else if ( !UserInputValidator.isPasswordValid(newPassword) ) {
            view.showToast("Entered password isn't correct for pattern, try again");
            view.showChangePasswordDialog();

        } else {
            view.showToast("Password was changed");
            authPreference.setAccountPassword(newPassword);
        }
    }

    @Override
    public void setThemePrimaryColor(int res) {
        themePreference.setPrimaryColor(res);
        view.customizeTheme();
        view.customizeRecycler();
        view.openParentItem(2);
        EventBus.getDefault().post(new ChangeThemeEvent(res));
    }
    @Override
    public void setThemeSecondaryColor(int res) {
        themePreference.setPrimaryColor(res);
        view.customizeTheme();
        view.customizeRecycler();
        EventBus.getDefault().post(new ChangeThemeEvent(res));
    }

    @Override
    public void setThemePrimaryColor(String colorHex) {
        themePreference.setPrimaryColor(colorHex);
        view.customizeTheme();
        view.customizeRecycler();
        view.openParentItem(2);
        EventBus.getDefault().post(new ChangeThemeEvent(colorHex));
    }

    @Override
    public void setThemeSecondaryColor(String colorHex) {
        themePreference.setPrimaryColor(colorHex);
        view.customizeTheme();
        view.customizeRecycler();
        EventBus.getDefault().post(new ChangeThemeEvent(Integer.parseInt(colorHex)));
    }

    @Override
    public void onPickColorClick() {
        view.showColorPicker();
    }

}
