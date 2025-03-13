package seedu.trip_buddy.exception;

/**
 * Thrown when a command keyword is not recognized.
 * */
public class InvalidKeywordException extends InvalidCommandException {

    private final String keyword;

    public InvalidKeywordException(String keyword) {
        super();
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
