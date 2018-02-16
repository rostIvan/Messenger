package trickyquestion.messenger.screen.login.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.ui.abstraction.AWithFieldFragment;
import trickyquestion.messenger.ui.abstraction.Layout;
import trickyquestion.messenger.util.java.validation.LoginValidator;

@Layout(res = R.layout.fragment_login_account)
public class LoginFragment extends AWithFieldFragment {

    @BindView(R.id.nick_field)
    EditText nickField;
    @BindView(R.id.pass_field)
    EditText passField;
    @BindView(R.id.button_create_account)
    TextView buttonSignIn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NotNull
    @Override
    public List<EditText> getAllEditable() { return Arrays.asList(nickField, passField); }

    @OnClick(R.id.button_create_account)
    public void createAccount() {
        final String login = nickField.getText().toString();
        final String password = passField.getText().toString();

        if (isValid(login, password)) createAccountWithData(login, password);
        else showError("Incorrect input");
    }

    private boolean isValid(final String login, final String password) {
        return LoginValidator.isValid(login, password);
    }

    private void createAccountWithData(final String login, final String password) {
        getHostActivity().saveAccountData(login, password);
        getHostActivity().startMainScreen();
    }

    private LoginScreenActivity getHostActivity() {
        return ((LoginScreenActivity) getActivity());
    }

}
