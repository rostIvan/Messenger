package trickyquestion.messenger.settings_screen.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import trickyquestion.messenger.R;
import trickyquestion.messenger.util.preference.ThemePreference;

public class ChangePasswordDialog extends DialogFragment implements IChangeDialog {

    private EditText editText;
    private DialogInterface.OnClickListener onPositiveClickListener;
    private DialogInterface.OnClickListener onNegativeClickListener;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        editText = new EditText(getActivity());
        builder
                .setTitle("New password")
                .setView(editText)
                .setIcon(R.drawable.ic_change_pass_primary_green)
                .setPositiveButton("Apply", onPositiveClickListener)
                .setNegativeButton("Cancel", onNegativeClickListener);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        customizeTheme();
    }

    private void customizeTheme() {
        final int primaryColor = new ThemePreference(getContext()).getPrimaryColor();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(primaryColor);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(primaryColor);
        editText.getBackground().mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public String getEnteredText() {
        return editText.getText().toString().trim();
    }

    @Override
    public void setOnPositiveClickListener(DialogInterface.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    @Override
    public void setOnNegativeClickListener(DialogInterface.OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }
}