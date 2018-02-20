package trickyquestion.messenger.screen.login.sign_up;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import trickyquestion.messenger.util.java.validation.SignUpValidator;

@Layout(res = R.layout.fragment_sign_up)
public class SignUpFragment extends AWithFieldFragment {

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

    @OnClick(R.id.button_create_account)
    public void createAccount() {
        final String login = getEnteredLogin();
        final String password = getEnteredPassword();

        if (isEnteredCorrect(login, password)) createAccountWithData(login, password);
        else showError("Incorrect input");
    }

    @NonNull
    private String getEnteredPassword() {
        return passField.getText().toString();
    }

    @NonNull
    private String getEnteredLogin() {
        return nickField.getText().toString();
    }

    private boolean isEnteredCorrect(final String login, final String password) {
        return SignUpValidator.isCorrect(login, password);
    }

    private void createAccountWithData(final String login, final String password) {
        getHostActivity().saveAccountData(login, password);
        getHostActivity().startMainScreen();
    }

    @NotNull
    @Override
    public List<EditText> getAllEditable() { return Arrays.asList(nickField, passField); }

    private SignUpActivity getHostActivity() {
        return ((SignUpActivity) getActivity());
    }
}
