package trickyquestion.messenger.settings_screen.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import trickyquestion.messenger.R;
import trickyquestion.messenger.util.preference.ThemePreference;

public class ChangePasswordDialog extends DialogFragment implements IChangeDialog {

    private LinearLayout container;
    private EditText newPasswordField;
    private EditText previousPasswordField;
    private DialogInterface.OnClickListener onPositiveClickListener;
    private DialogInterface.OnClickListener onNegativeClickListener;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        initViews();
        builder
                .setTitle(getCustomizeTitle("New password"))
                .setView(container)
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

    private void initViews() {
        container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        newPasswordField = new EditText(getActivity());
        previousPasswordField = new EditText(getActivity());
        container.addView(previousPasswordField);
        container.addView(newPasswordField);
    }

    private void customizeTheme() {
        final int primaryColor = new ThemePreference(getContext()).getPrimaryColor();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(primaryColor);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(primaryColor);

        previousPasswordField.getBackground().mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
        previousPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        previousPasswordField.setHint("Previous password");
        previousPasswordField.setHintTextColor(Color.GRAY);
        previousPasswordField.setLayoutParams(getContainerParams());

        newPasswordField.getBackground().mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
        newPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPasswordField.setHint("New password");
        newPasswordField.setHintTextColor(Color.GRAY);
        newPasswordField.setLayoutParams(getContainerParams());
    }

    public String getNewPasswordText() {
        return newPasswordField.getText().toString().trim();
    }
    public String getPreviousPasswordText() {
        return previousPasswordField.getText().toString().trim();
    }

    @Override
    public String getEnteredText() {
        return "previous: " + getPreviousPasswordText() + " new: " + getNewPasswordText();
    }

    @Override
    public void setOnPositiveClickListener(DialogInterface.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    @Override
    public void setOnNegativeClickListener(DialogInterface.OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    private SpannableString getCustomizeTitle(@NotNull final String title) {
        final int color = new ThemePreference(getContext()).getPrimaryColor();
        final SpannableString str = new SpannableString(title);
        str.setSpan(new ForegroundColorSpan(color), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

    private LinearLayout.LayoutParams getContainerParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);
        return params;
    }
}