package trickyquestion.messenger.screen.login.ask_password;

import android.os.Bundle;
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
import trickyquestion.messenger.ui.ALoginFragment;


public class AskPasswordFragment extends ALoginFragment {

    @BindView(R.id.hello_text)
    TextView textViewHello;
    @BindView(R.id.pass_ask_field)
    EditText passField;
    @BindView(R.id.button_sign_in)
    TextView buttonSignIn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String helloMessage = String.format("Hello, %s [%s]",
                getHostActivity().getUserNick(), getHostActivity().getUserPassword());
        textViewHello.setText(helloMessage);
    }

    @Override
    public int getLayout() { return R.layout.fragment_ask_pass; }

    @NotNull
    @Override
    public List<EditText> getAllEditable() { return Collections.singletonList(passField); }

    @OnClick(R.id.button_sign_in)
    public void onButtonSignInClick() {
        if (passIsCorrect()) signInAccount();
        else showError("Incorrect password, try again!");
    }

    private boolean passIsCorrect() {
        final String enteredPass = passField.getText().toString();
        final String realPass = getHostActivity().getUserPassword();
        return enteredPass.equals(realPass);
    }

    private void signInAccount() {
        getHostActivity().signInAccount();
    }

    private AskPasswordActivity getHostActivity() {
        return ((AskPasswordActivity) getActivity());
    }
}
