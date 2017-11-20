package trickyquestion.messenger.login_screen.ask_password;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
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
import trickyquestion.messenger.main_screen.view.MainActivity;
import trickyquestion.messenger.util.preference.AuthPreference;
import trickyquestion.messenger.util.preference.ThemePreference;


public class AskPasswordFragment extends Fragment {

    @BindView(R.id.hello_text)
    TextView textViewHello;
    @BindView(R.id.pass_ask_field)
    EditText pass;
    @BindView(R.id.button_sign_in)
    TextView buttonSignIn;

    private AuthPreference authPreference;
    private ThemePreference themePreference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ask_pass, container, false);
        ButterKnife.bind(this, view);
        authPreference = new AuthPreference(this.getContext());
        themePreference = new ThemePreference(this.getContext());
        textViewHello.setText(
                "Hello, "
                .concat(authPreference.getAccountLogin())
                .concat(" ")
                .concat(authPreference.getAccountPassword())
        );
        customizeTheme();
        addPassListeners();
        return view;
    }

    private void customizeTheme() {
        pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryGreen), PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.button_sign_in)
    public void onButtonSignInClick() {
        if (rightPass()) signInAccount();
        else {
            pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPassListeners() {
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0)
                    pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryGreen), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private boolean rightPass() {
        return pass.getText().toString().equals(authPreference.getAccountPassword());
    }
    private void signInAccount() {
        authPreference.setUserAuthenticated(true);
        getActivity().startActivity(new Intent(this.getContext(), MainActivity.class));
        getActivity().finish();
    }
}
