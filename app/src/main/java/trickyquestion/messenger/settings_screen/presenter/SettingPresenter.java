package trickyquestion.messenger.settings_screen.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.R;
import trickyquestion.messenger.settings_screen.expand_list.list_data.ExpandedListCreator;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingChild;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingParent;
import trickyquestion.messenger.settings_screen.view.ISettingView;
import trickyquestion.messenger.util.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.preference.AuthPreference;
import trickyquestion.messenger.util.preference.ThemePreference;
import trickyquestion.messenger.util.validation.UserInputValidator;

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
    public void setNewPassword(String password) {
        if (UserInputValidator.isPasswordValid(password)) {
            view.showToast("Password was changed");
            authPreference.setAccountPassword(password);
        } else {
            view.showToast("Entered password isn't correct, try again");
            view.showChangePasswordDialog();
        }
    }

    @Override
    public void setThemePrimaryColor(int res) {
        themePreference.setPrimaryColor(res);
        view.customizeTheme();
        view.customizeRecycler();
        EventBus.getDefault().post(new ChangeThemeEvent(res));
    }

    @Override
    public void setThemeSecondaryColor(int res) {
        themePreference.setPrimaryColor(res);
        view.customizeTheme();
        view.customizeRecycler();
        EventBus.getDefault().post(new ChangeThemeEvent(res));
    }
}
