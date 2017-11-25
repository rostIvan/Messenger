package trickyquestion.messenger.util.validation;


import android.content.Context;

import java.util.regex.Pattern;

import trickyquestion.messenger.util.preference.AuthPreference;

public class UserInputValidator {

    public static boolean isLoginValid(final String login) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(login).matches();
    }

    public static boolean isPasswordValid(final String password) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(password).matches();
    }

    public static boolean isPreviousPasswordCorrect(final String input, final Context context) {
        final String password = new AuthPreference(context).getAccountPassword();
        return password.equals(input);
    }
}
