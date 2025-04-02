package seedu.tripbuddy.exception;

public class MissingOptionException extends InvalidCommandException {

    private final String missingOpt;

    public MissingOptionException(String missingOpt) {
        super();
        this.missingOpt = missingOpt;
    }
}
