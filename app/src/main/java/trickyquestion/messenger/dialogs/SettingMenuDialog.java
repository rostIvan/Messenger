package trickyquestion.messenger.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.util.Constants;

public class SettingMenuDialog {
    private Dialog dialog;
    private boolean isShow;

    private CheckBox checkBoxAskPassword;
    private Button buttonLogOut;
    private Button buttonChangeDate;
    private CircleImageView image;
    private TextView loginName;
    private TextView id;

    private SharedPreferences preferences;

    public static final String EXTRA_ASK_PASSWORD = "boxWasChecked";

    public SettingMenuDialog(final Context context) {
        dialog = new Dialog(context, R.style.CustomDialog);
        initSharedPreferences(context);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
        doKeepDialog(dialog);
    }

    private void initSharedPreferences (final Context context) {
        preferences = context.getSharedPreferences(Constants.EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
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
        buttonChangeDate = (Button) dialog.findViewById(R.id.button_change_data);
        checkBoxAskPassword = (CheckBox) dialog.findViewById(R.id.check_box_ask_pass);
        loginName = (TextView) dialog.findViewById(R.id.setting_login_name);
        loginName = (TextView) dialog.findViewById(R.id.setting_login_name);
        id = (TextView) dialog.findViewById(R.id.setting_id);
        image = (CircleImageView) dialog.findViewById(R.id.setting_circle_image);

        loginName.setText(preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN, "admin"));
        id.setText("id: ".concat(UUID.randomUUID().toString().substring(0, 20).concat(" ...")));
        image.setImageDrawable(dialog.getContext().getResources().getDrawable(R.mipmap.ic_launcher));
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
        buttonChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlert();
            }
        });
    }

    private void clearAccountDate() {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_LOGIN, null);
        editor.putString(Constants.EXTRA_KEY_AUTH_PASSWORD, null);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, false);

        editor.apply();
        editor.commit();
    }

    private void setAccountDate(final String login, final String password) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_AUTH_LOGIN, login);
        editor.putString(Constants.EXTRA_KEY_AUTH_PASSWORD, password);
        editor.putBoolean(Constants.EXTRA_KEY_IS_AUTHENTICATED, true);

        editor.apply();
        editor.commit();
    }

    private void restartApp(final View view) {
        Intent i = view.getContext().getPackageManager()
                .getLaunchIntentForPackage(view.getContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(i);
    }
    private void restartApp(final Context context) {
        Intent i = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    private void buildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
        final EditText inputLogin = new EditText(dialog.getContext());
        final EditText inputPass = new EditText(dialog.getContext());
        final TextView textLoginTitle = new TextView(dialog.getContext());
        final TextView textPassTitle = new TextView(dialog.getContext());
        final LinearLayout parent = new LinearLayout(dialog.getContext());

        parent.setOrientation(LinearLayout.VERTICAL);
        textLoginTitle.setText("Login: ");
        textPassTitle.setText("Password: ");
        inputLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        inputPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        parent.addView(textLoginTitle);
        parent.addView(inputLogin);
        parent.addView(textPassTitle);
        parent.addView(inputPass);

        builder.setView(parent);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                final String inputLoginText = inputLogin.getText().toString();
                final String inputLoginPass = inputPass.getText().toString();
                setAccountDate(inputLoginText, inputLoginPass);
                restartApp(dialog.getContext());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private static void doKeepDialog(Dialog dialog){
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }
}
