package seedu.trip_buddy.exception;

public abstract class TripBuddyException extends Exception {

    public TripBuddyException() {
        super();
    }

    public TripBuddyException(String message) {
        super(message);
    }
}
