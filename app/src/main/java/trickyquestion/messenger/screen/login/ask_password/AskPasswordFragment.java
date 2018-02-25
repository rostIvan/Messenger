package trickyquestion.messenger.screen.login.ask_password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.ui.abstraction.fragment.AWithFieldFragment;
import trickyquestion.messenger.ui.abstraction.interfaces.Layout;
import trickyquestion.messenger.util.java.validation.PassValidator;

@Layout(res = R.layout.fragment_ask_pass)
public class AskPasswordFragment extends AWithFieldFragment {

    @BindView(R.id.hello_text)
    TextView textViewHello;
    @BindView(R.id.pass_ask_field)
    EditText passField;
    @BindView(R.id.button_sign_in)
    TextView buttonSignIn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String helloMessage = String.format("Hello, %s pass[%s]", getUserNick(), getUserPassword());
        textViewHello.setText(helloMessage);
    }

    @NotNull
    @Override
    public List<EditText> getAllEditable() { return Collections.singletonList(passField); }

    @OnClick(R.id.button_sign_in)
    public void onButtonSignInClick() {
        if (passIsCorrect()) signInAccount();
        else showError("Incorrect password, try again!");
    }

    public boolean passIsCorrect() {
        final String enteredPass = getEnteredPass();
        final String realPass = getUserPassword();
        return PassValidator.isCorrect(enteredPass, realPass);
    }

    public void signInAccount() {
        getHostActivity().signInAccount();
    }

    @NonNull
    public String getEnteredPass() {
        return passField.getText().toString();
    }

    @NonNull
    private String getUserNick() {
        return getHostActivity().getUserNick();
    }

    @NonNull
    public String getUserPassword() {
        return getHostActivity().getUserPassword();
    }
    private AskPasswordActivity getHostActivity() {
        return ((AskPasswordActivity) getActivity());
    }
}
