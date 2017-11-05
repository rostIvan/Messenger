package trickyquestion.messenger.util.validation;


import java.util.regex.Pattern;

public class UserInputValidator {

    public static boolean isLoginValid(final String login) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(login).matches();
    }

    public static boolean isPasswordValid(final String password) {
        final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{3,}$");
        return pattern.matcher(password).matches();
    }

}
