package trickyquestion.messenger.util.java.validation;

public class PassValidator {
    private final String enteredPass;
    private final String realPass;

    public PassValidator(final String enteredPass, final String realPass) {
        this.enteredPass = enteredPass;
        this.realPass = realPass;
    }

    public boolean isCorrect() {
        return enteredPass.equals(realPass);
    }

    public static boolean isCorrect(final String enteredPass, final String realPass) {
        return new PassValidator(enteredPass, realPass).isCorrect();
    }
}
