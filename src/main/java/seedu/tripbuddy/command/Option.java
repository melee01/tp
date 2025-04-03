package seedu.tripbuddy.command;

/**
 * Stores an option-value pair, {@code '-'} excluded.
 */
public record Option(String opt, String val) {

    @Override
    public String toString() {
        if (opt.isEmpty()) {
            return val;
        }
        if (val.isEmpty()) {
            return "-" + opt;
        }
        return "-" + opt + ": " + val;
    }
}
