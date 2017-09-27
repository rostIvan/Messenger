package trickyquestion.messenger.login_screen.authentication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.util.validation.LoginValidator;

public class LoginFragment extends Fragment {
    @BindView(R.id.nick_field)
    EditText nickField;
    @BindView(R.id.pass_field)
    EditText passFiled;
    @BindView(R.id.button_create_account)
    TextView buttonSignIn;

    public static final String EXTRA_TAG_AUTH_LOGIN = "login";
    public static final String EXTRA_TAG_AUTH_PASS = "pass";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        ButterKnife.bind(this, view);
        setEditTextLineColor(getResources().getColor(R.color.colorPrimaryGreen));
        setupListeners();
        return view;
    }

    private void setEditTextLineColor (final int color) {
        passFiled.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        nickField.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void setupListeners() {
        nickField.addTextChangedListener(new LoginTextChangeListener());
        passFiled.addTextChangedListener(new LoginTextChangeListener());
    }

    @OnClick(R.id.button_create_account)
    public void createAccount() {
        final String login = nickField.getText().toString();
        final String password = passFiled.getText().toString();

        if (isValid(login, password)) {
            final Intent intent = new Intent();
            intent.putExtra(EXTRA_TAG_AUTH_LOGIN, nickField.getText().toString());
            intent.putExtra(EXTRA_TAG_AUTH_PASS, passFiled.getText().toString());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
        else {
            nickField.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);
            passFiled.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(this.getContext(), "Incorrect input", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(final String login, final String password) {
        final LoginValidator validator = new LoginValidator(login, password);
        return validator.isInputValid();
    }

    private class LoginTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0)
                setEditTextLineColor(getResources().getColor(R.color.colorPrimaryGreen));
        }
    }
}
