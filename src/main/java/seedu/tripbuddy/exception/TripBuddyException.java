package seedu.tripbuddy.exception;

public abstract class TripBuddyException extends Exception {

    public TripBuddyException() {
        super();
    }

    public TripBuddyException(String message) {
        super(message);
    }
}
