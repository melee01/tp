package seedu.tripbuddy.exception;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;

public class ExceptionHandler {

    public static String handleInvalidKeywordException(InvalidKeywordException e) {
        String keyword = e.getKeyword();
        return "Invalid command keyword: \"" + keyword +
                "\". Please review the user guide for more information.";
    }

    public static String handleInvalidArgumentException(InvalidArgumentException e) {
        String argument = e.getArgument();
        String message = e.getMessage();
        if (message != null) {
            return "Invalid command argument: \"" + argument +
                    "\": " + message;
        }
        return "Invalid command argument: \"" + argument +
                "\". Please review the user guide for more information.";
    }

    public static String handleNumberFormatException() {
        return "Uh oh! Invalid number format. Please enter a valid integer.";
    }

    public static String handleMissingOptionException(MissingOptionException e) {
        String missingOpt = e.getMissingOpt();
        if (e.getMissingOpt().isEmpty()) {
            return "Oh no. Cannot not find the main argument.";

        }
        return "Oh no. Cannot not find option label: -" + missingOpt;
    }

    public static String handleDateTimeParseException(DateTimeParseException e) {
        return "Invalid date/time format! Please use yyyy-MM-dd HH:mm:ss\n\t" + e.getParsedString();
    }

    public static String handleJSONException(JSONException e) {
        return "Uh oh! Missing or corrupt data found in your save file:\n\t" + e.getMessage();
    }

    public static String handleFileNotFoundException(FileNotFoundException e) {
        return "No save file detected.";
    }

    public static String handleException(Exception e) {
        return e.getMessage();
    }
}
