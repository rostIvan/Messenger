package trickyquestion.messenger.settings_screen.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingChild;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingParent;
import trickyquestion.messenger.settings_screen.view.ISettingView;
import trickyquestion.messenger.util.preference.AuthPreference;
import trickyquestion.messenger.util.validation.UserInputValidator;

public class SettingPresenter implements ISettingPresenter {

    private final ISettingView view;
    private final AuthPreference authPreference;

    public SettingPresenter(ISettingView view) {
        this.view = view;
        this.authPreference = new AuthPreference(view.getContext());
    }

    @Override
    public ISettingView getView() {
        return view;
    }

    @Override
    public View.OnClickListener onBackPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }

    @Override
    public List<ParentObject> getParents() {
        final List<ParentObject> parents = new ArrayList<>();

        final SettingParent parent1 = getFirstParent();
        final SettingParent parent2 = getSecondParent();
        final SettingParent parent3 = getThirdParent();
        parent1.setChildObjectList(getChildsFirstParent());

        parents.add(parent1);
        parents.add(parent2);
        parents.add(parent3);

        return parents;
    }

    private SettingParent getFirstParent() {
        final SettingParent parent1 = new SettingParent();
        parent1.setTitle("Account Setting");
        parent1.setImageResource(R.drawable.ic_settings_primary_green);
        return parent1;
    }

    private SettingParent getSecondParent() {
        final SettingParent parent2 = new SettingParent();
        parent2.setTitle("Notification");
        parent2.setImageResource(R.drawable.ic_notification_primary_green);
        return parent2;
    }


    private SettingParent getThirdParent() {
        final SettingParent parent3 = new SettingParent();
        parent3.setTitle("Color managment");
        parent3.setImageResource(R.drawable.ic_color_managment_primary_green);
        return parent3;
    }


    private List<Object> getChildsFirstParent() {
        final SettingChild child1 = new SettingChild(
                "Change name", false, R.drawable.ic_change_name_primary_green, false
        );
        final SettingChild child2 = new SettingChild(
                "Change password", false, R.drawable.ic_change_pass_primary_green, false
        );
        final SettingChild child3 = new SettingChild(
                "Ask for password", authPreference.askPassword(), R.drawable.ic_ask_pass_primary_green, false
        );
        final SettingChild child4 = new SettingChild(
                "Log out", false, R.drawable.ic_logout_primary_green, true
        );
        final List<Object> childs = new ArrayList<>();
        childs.add(child1);
        childs.add(child2);
        childs.add(child3);
        childs.add(child4);
        return childs;
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


}
