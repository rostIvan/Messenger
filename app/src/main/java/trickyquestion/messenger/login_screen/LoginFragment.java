package trickyquestion.messenger.login_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.main_screen.view.MainActivity;
import trickyquestion.messenger.R;

public class LoginFragment extends Fragment {
    @BindView(R.id.nick_field)
    TextView nickField;
    @BindView(R.id.pass_field)
    TextView passFiled;
    @BindView(R.id.button_sign_in)
    TextView buttonSignIn;

    public static final String EXTRA_TAG_LOGIN_NICK = "nick";
    public static final String EXTRA_TAG_LOGIN_PASS = "pass";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_sign_in)
    public void signIn() {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_TAG_LOGIN_NICK, nickField.getText().toString());
        intent.putExtra(EXTRA_TAG_LOGIN_PASS, passFiled.getText().toString());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
