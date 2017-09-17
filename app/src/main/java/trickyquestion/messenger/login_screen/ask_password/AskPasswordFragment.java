package trickyquestion.messenger.login_screen.ask_password;

import android.content.Context;
import android.content.SharedPreferences;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.presenter.MainPresenter;
import trickyquestion.messenger.main_screen.view.dialogs.SettingMenuDialog;


public class AskPasswordFragment extends Fragment {

    @BindView(R.id.hello_text)
    TextView textViewHello;
    @BindView(R.id.pass_ask_field)
    EditText pass;
    @BindView(R.id.button_sign_in)
    TextView buttonSignIn;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ask_pass, container, false);
        ButterKnife.bind(this, view);
        preferences = view.getContext().getSharedPreferences(MainPresenter.EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
        textViewHello.setText(
                "Hello, "
                .concat(preferences.getString(MainPresenter.EXTRA_KEY_AUTH_LOGIN, "someone"))
                .concat(" ")
                .concat(preferences.getString(MainPresenter.EXTRA_KEY_AUTH_PASSWORD, "someone pass"))
        );
        addPassListeners();
        return view;
    }

    @OnClick(R.id.button_sign_in)
    public void onButtonSignInClick() {
        if (rightPass()) signInAccount();
        else Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
    }

    private void addPassListeners() {
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (rightPass()) signInAccount();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private boolean rightPass() {
        return pass.getText().toString().equals(preferences.getString(MainPresenter.EXTRA_KEY_AUTH_PASSWORD, "someone pass"));
    }
    private void signInAccount() {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SettingMenuDialog.EXTRA_PASSWORD_WAS_ENTER, true);
        editor.apply();
        editor.commit();
        getActivity().finish();
        Toast.makeText(getContext(), "True", Toast.LENGTH_SHORT).show();
    }
}
