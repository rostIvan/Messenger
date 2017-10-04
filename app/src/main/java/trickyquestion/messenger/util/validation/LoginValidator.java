package trickyquestion.messenger.util.validation;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

public class LoginValidator {

    private final String name;
    private final String pass;

    public LoginValidator(@NonNull final String name, @NonNull final String pass) {
        this.name = name;
        this.pass = pass;
    }

    public boolean isInputValid() {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(name).matches() && pattern.matcher(pass).matches();
    }
}
