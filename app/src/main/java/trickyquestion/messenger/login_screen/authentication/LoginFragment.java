package trickyquestion.messenger.login_screen.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.R;

public class LoginFragment extends Fragment {
    @BindView(R.id.nick_field)
    TextView nickField;
    @BindView(R.id.pass_field)
    TextView passFiled;
    @BindView(R.id.button_create_account)
    TextView buttonSignIn;

    public static final String EXTRA_TAG_AUTH_LOGIN = "login";
    public static final String EXTRA_TAG_AUTH_PASS = "pass";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    }

    private boolean isValid(final String login, final String password) {
        return true;
    }
}
