package seedu.tripbuddy.exception;

public class InvalidArgumentException extends InvalidCommandException {

    private final String argument;

    public InvalidArgumentException(String argument) {
        super();
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }
}
