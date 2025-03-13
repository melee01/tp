package seedu.tripbuddy.exception;

/**
 * Thrown when a command cannot be correctly parsed.
 * */
public abstract class InvalidCommandException extends TripBuddyException {

    public InvalidCommandException() {
        super();
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
