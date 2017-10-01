package trickyquestion.messenger.util.validation;

import android.support.annotation.NonNull;

public class LoginValidator {

    private final String name;
    private final String pass;

    public LoginValidator(@NonNull final String name, @NonNull final String pass) {
        this.name = name;
        this.pass = pass;
    }

    public boolean isInputValid() {
        // TODO: 27.09.17 Create logic
        return true;
    }
}
