package seedu.trip_buddy.exception;

import seedu.trip_buddy.framework.Ui;

public class ExceptionHandler {

    private final Ui ui;

    public ExceptionHandler(Ui ui) {
        this.ui = ui;
    }

    public void handleInvalidKeywordException(InvalidKeywordException e) {
        String keyword = e.getKeyword();
        ui.printMessage("Invalid command keyword: " + keyword +
                ". Please review the user guide for more information.");
    }

    public void handleNumberFormatException(NumberFormatException e) {
        ui.printMessage("Uh oh! Invalid number format. Please enter a valid integer.");
    }
}
