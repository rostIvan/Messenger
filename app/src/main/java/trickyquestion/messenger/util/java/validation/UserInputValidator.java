package trickyquestion.messenger.util.java.validation;


import java.util.regex.Pattern;

import trickyquestion.messenger.util.android.preference.AuthPreference;

public class UserInputValidator {

    private UserInputValidator() {}

    public static boolean isLoginValid(final String login) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(login).matches();
    }

    public static boolean isPasswordValid(final String password) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(password).matches();
    }

    public static boolean isPreviousPasswordCorrect(final String input, final AuthPreference authPreference) {
        final String password = authPreference.getAccountPassword();
        return password.equals(input);
    }
}
