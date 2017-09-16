package trickyquestion.messenger.main_screen.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.presenter.MainPresenter;

public class SettingMenuDialog {
    private Dialog dialog;
    private boolean isShow;

    public SettingMenuDialog(final Context context) {
        dialog = new Dialog(context, R.style.CustomDialog);
        create();
    }
    public void show() {
        dialog.show();
        isShow = true;
    }

    private void create() {
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

        dialog.findViewById(R.id.button_clear_auth_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAccountDate(view);
                restartApp(view);
            }
        });
    }

    public boolean isShow() {
        return isShow;
    }

    private void clearAccountDate(View view) {
        final SharedPreferences preferences =
                view.getContext().getSharedPreferences(MainPresenter.EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_LOGIN, null);
        editor.putString(MainPresenter.EXTRA_KEY_AUTH_PASSWORD, null);
        editor.putBoolean(MainPresenter.EXTRA_KEY_IS_AUTHENTICATED, false);

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
