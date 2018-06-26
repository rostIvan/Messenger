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
import trickyquestion.messenger.ui.fragment.AWithFieldFragment;
import trickyquestion.messenger.ui.interfaces.Layout;

import static trickyquestion.messenger.data.util.TextUtilKt.textOf;

@Layout(res = R.layout.fragment_ask_pass)
public class AskPasswordFragment extends AWithFieldFragment {

    @BindView(R.id.hello_text) TextView textViewHello;
    @BindView(R.id.pass_ask_field) EditText passField;

    private AskPasswordInteractor interactor;

    public void attach(AskPasswordInteractor passwordInteractor) {
        this.interactor = passwordInteractor;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String helloMessage = interactor.getGreeting();
        textViewHello.setText(helloMessage);
    }

    @OnClick(R.id.button_sign_in)
    public void onButtonSignInClick() {
        final String enteredPassword = textOf(passField);
        interactor.dataProcess(enteredPassword);
    }

    @NotNull @Override
    public List<EditText> getAllEditable() { return Collections.singletonList(passField); }
}
