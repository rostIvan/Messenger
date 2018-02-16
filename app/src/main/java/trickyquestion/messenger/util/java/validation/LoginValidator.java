package trickyquestion.messenger.util.java.validation;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

public class LoginValidator {

    private final String name;
    private final String pass;

    public LoginValidator(@NonNull final String name, @NonNull final String pass) {
        this.name = name;
        this.pass = pass;
    }

    public boolean isValid() {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(name).matches() && pattern.matcher(pass).matches();
    }

    public static boolean isValid(@NonNull final String name, @NonNull final String pass) {
        return new LoginValidator(name, pass).isValid();
    }
}
