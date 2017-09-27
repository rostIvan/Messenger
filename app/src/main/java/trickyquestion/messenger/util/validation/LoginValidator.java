package trickyquestion.messenger.util.validation;

public class LoginValidator {

    private final String name;
    private final String pass;

    public LoginValidator(final String name, final String pass) {
        this.name = name;
        this.pass = pass;
    }

    public boolean isInputValid() {
        // TODO: 27.09.17 Create logic
        return true;
    }
}
