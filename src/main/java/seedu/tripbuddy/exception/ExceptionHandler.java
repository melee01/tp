package seedu.tripbuddy.exception;

import seedu.tripbuddy.framework.Ui;
import java.time.format.DateTimeParseException;

public class ExceptionHandler {

    public static void handleInvalidKeywordException(InvalidKeywordException e) {
        String keyword = e.getKeyword();
        Ui.printMessage("Invalid command keyword: \"" + keyword +
                "\". Please review the user guide for more information.");
    }

    public static void handleInvalidArgumentException(InvalidArgumentException e) {
        String argument = e.getArgument();
        Ui.printMessage("Invalid command argument: \"" + argument +
                "\". Please review the user guide for more information.");
    }

    public static void handleNumberFormatException() {
        Ui.printMessage("Uh oh! Invalid number format. Please enter a valid integer.");
    }

    public static void handleMissingOptionException(MissingOptionException e) {
        String missingOpt = e.getMissingOpt();
        Ui.printMessage("Oh no. Cannot not find option label: -" + missingOpt);
    }

    public static void handleDateTimeParseException(DateTimeParseException e) {
        Ui.printMessage("Invalid date/time format! Please use yyyy-MM-dd HH:mm:ss\n\t" + e.getParsedString());
    }

    public static void handleException(Exception e) {
        Ui.printMessage(e.getMessage());
    }
}
