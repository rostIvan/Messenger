package trickyquestion.messenger.screen.login.sign_up;

import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.ui.fragment.AWithFieldFragment;
import trickyquestion.messenger.ui.interfaces.Layout;

import static trickyquestion.messenger.data.util.TextUtilKt.textOf;

@Layout(res = R.layout.fragment_sign_up)
public class SignUpFragment extends AWithFieldFragment {

    @BindView(R.id.nick_field) EditText nickField;
    @BindView(R.id.pass_field) EditText passField;
    private SignUpViewModel viewModel;

    public void attach(SignUpViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @OnClick(R.id.button_create_account)
    public void tryCreateAccount() {
        final String login = textOf(nickField);
        final String password = textOf(passField);
        viewModel.onSignUpButtonClick(login, password, new SignUpViewModel.SignUpCallback() {
            @Override public void onSuccess() { viewModel.createAccount(login, password); }
            @Override public void onError() { showError("Incorrect input"); }
        });
    }

    @NotNull @Override
    public List<EditText> getAllEditable() { return Arrays.asList(nickField, passField); }
}
