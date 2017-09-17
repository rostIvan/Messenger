package trickyquestion.messenger.main_screen.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.presenter.MainPresenter;

public class SettingMenuDialog {
    private Dialog dialog;
    private boolean isShow;

    private CheckBox checkBoxAskPassword;
    private Button buttonLogOut;

    private SharedPreferences preferences;

    public static final String EXTRA_ASK_PASSWORD = "boxWasChecked";
    public static final String EXTRA_PASSWORD_WAS_ENTER = "passWasEnter";

    public SettingMenuDialog(final Context context) {
        dialog = new Dialog(context, R.style.CustomDialog);
        initSharedPreferences(context);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
    }

    private void initSharedPreferences (final Context context) {
        preferences = context.getSharedPreferences(MainPresenter.EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
    }

    private void create() {
        customizeDialog();
        initView();
        setupListeners();
    }

    public boolean isShow() {
        return isShow;
    }


    private void customizeDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        dialog.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShow = false;
            }
        });
    }


    private void initView() {
        buttonLogOut = (Button) dialog.findViewById(R.id.button_clear_auth_data);
        checkBoxAskPassword = (CheckBox) dialog.findViewById(R.id.check_box_ask_pass);
        checkBoxAskPassword.setChecked(preferences.getBoolean(EXTRA_ASK_PASSWORD, false));
    }

    private void setupListeners() {
        checkBoxAskPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences.Editor editor = preferences.edit();
                if (checkBoxAskPassword.isChecked()) editor.putBoolean(EXTRA_ASK_PASSWORD, true);
                else editor.putBoolean(EXTRA_ASK_PASSWORD, false);
                editor.apply();
                editor.commit();
            }
        });
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAccountDate();
                restartApp(view);
            }
        });
    }

    private void clearAccountDate() {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_LOGIN, null);
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_PASSWORD, null);
        editor.putBoolean(MainPresenter.EXTRA_KEY_IS_AUTHENTICATED, false);

        editor.apply();
        editor.commit();
    }

    private void setAccountDate(final String login, final String password) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_LOGIN, login);
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_PASSWORD, password);
        editor.putBoolean(MainPresenter.EXTRA_KEY_IS_AUTHENTICATED, true);

        editor.apply();
        editor.commit();
    }

    private void restartApp(View view) {
        Intent i = view.getContext().getPackageManager()
                .getLaunchIntentForPackage(view.getContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(i);
    }
}
