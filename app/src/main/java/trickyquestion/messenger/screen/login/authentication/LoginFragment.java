package trickyquestion.messenger.screen.login.authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.util.java.validation.DataValidator;

import static trickyquestion.messenger.util.ContextExtensionsKt.toast;
import static trickyquestion.messenger.util.ViewUtilKt.greenColor;
import static trickyquestion.messenger.util.ViewUtilKt.onTextChanged;
import static trickyquestion.messenger.util.ViewUtilKt.redColor;
import static trickyquestion.messenger.util.ViewUtilKt.setLineColor;

public class LoginFragment extends Fragment {

    @BindView(R.id.nick_field)
    EditText nickField;
    @BindView(R.id.pass_field)
    EditText passField;
    @BindView(R.id.button_create_account)
    TextView buttonSignIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setEditTextLineColor(greenColor(getContext()));
        setupListeners();
    }

    @OnClick(R.id.button_create_account)
    public void createAccount() {
        final String login = nickField.getText().toString();
        final String password = passField.getText().toString();

        if (isValid(login, password)) createAccountWithData(login, password);
        else showError();
    }

    private boolean isValid(final String login, final String password) {
        final DataValidator registration = new DataValidator(login, password);
        return registration.isValid();
    }

    private void createAccountWithData(final String login, final String password) {
        getHostActivity().saveAccountData(login, password);
        getHostActivity().startMainScreen();
    }

    private void showError() {
        toast(getContext(), "Incorrect input!");
        setEditTextLineColor(redColor(getContext()));
    }

    private void setEditTextLineColor(final int color) {
        setLineColor(nickField, color);
        setLineColor(passField, color);
    }

    private void setupListeners() {
        onTextChanged(nickField, this::checkEmptyInput);
        onTextChanged(passField, this::checkEmptyInput);
    }

    private void checkEmptyInput(final String changedText) {
        if(changedText.isEmpty())
            setEditTextLineColor(greenColor(getContext()));
    }

    private LoginScreenActivity getHostActivity() {
        return ((LoginScreenActivity) getActivity());
    }
}
