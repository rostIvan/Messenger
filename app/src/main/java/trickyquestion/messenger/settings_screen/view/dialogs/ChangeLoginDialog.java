package trickyquestion.messenger.settings_screen.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import trickyquestion.messenger.R;
import trickyquestion.messenger.util.preference.ThemePreference;

public class ChangeLoginDialog extends DialogFragment implements IChangeDialog {

    private EditText editText;
    private DialogInterface.OnClickListener onPositiveClickListener;
    private DialogInterface.OnClickListener onNegativeClickListener;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.editText = new EditText(getActivity());
        builder
                .setTitle(getCustomizeTitle("New login"))
                .setView(editText)
                .setIcon(R.drawable.ic_change_name_primary_green)
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
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
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

    private SpannableString getCustomizeTitle(@NotNull final String title) {
        final int color = new ThemePreference(getContext()).getPrimaryColor();
        final SpannableString str = new SpannableString(title);
        str.setSpan(new ForegroundColorSpan(color), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
