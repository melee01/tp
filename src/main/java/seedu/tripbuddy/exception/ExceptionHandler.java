package seedu.tripbuddy.exception;

import seedu.tripbuddy.framework.Ui;

public class ExceptionHandler {

    public static void handleInvalidKeywordException(InvalidKeywordException e) {
        String keyword = e.getKeyword();
        Ui.printMessage("Invalid command keyword: \"" + keyword +
                "\". Please review the user gUide for more information.");
    }

    public static void handleInvalidArgumentException(InvalidArgumentException e) {
        String argument = e.getArgument();
        Ui.printMessage("Invalid command argument: \"" + argument +
                "\". Please review the user gUide for more information.");
    }

    public static void handleNumberFormatException() {
        Ui.printMessage("Uh oh! Invalid number format. Please enter a valid integer.");
    }

    public static void handleArrayIndexOutOfBoundsException() {
        Ui.printMessage("Uh oh! Not enough arguments for this command.");
    }
}
