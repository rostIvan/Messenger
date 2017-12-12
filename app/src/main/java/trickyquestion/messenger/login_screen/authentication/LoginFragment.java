package trickyquestion.messenger.login_screen.authentication;

import android.content.Intent;
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

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.main_screen.view.MainActivity;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.util.preference.AuthPreference;
import trickyquestion.messenger.util.validation.RegistrationDataValidator;

public class LoginFragment extends Fragment {
    @BindView(R.id.nick_field)
    EditText nickField;
    @BindView(R.id.pass_field)
    EditText passFiled;
    @BindView(R.id.button_create_account)
    TextView buttonSignIn;

    private AuthPreference authPreference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        ButterKnife.bind(this, view);
        setEditTextLineColor(getResources().getColor(R.color.colorPrimaryGreen));
        setupListeners();
        authPreference = new AuthPreference(this.getContext());
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
            saveAccountData(login, password);
            startMainScreen();
        }
        else {
            setEditTextLineColor(getResources().getColor(R.color.colorRed));
            Toast.makeText(this.getContext(), "Incorrect input", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAccountData(String login, String password) {
        authPreference.setAccountId(UUID.randomUUID().toString());
        authPreference.setAccountLogin(login);
        authPreference.setAccountPassword(password);
        FriendsRepository.addFriend(
                new Friend(
                        authPreference.getAccountLogin(),
                        UUID.fromString(authPreference.getAccountId()),
                        null,
                        true
                )
        );
    }

    private void startMainScreen() {
        getActivity().startActivity(new Intent(this.getContext(), MainActivity.class));
        P2PProtocolConnector.TryStart(this.getContext());
        getActivity().finish();
    }

    private boolean isValid(final String login, final String password) {
        final RegistrationDataValidator validator = new RegistrationDataValidator(login, password);
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
