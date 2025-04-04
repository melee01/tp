package seedu.tripbuddy;

import org.json.JSONException;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.framework.InputHandler;
import seedu.tripbuddy.framework.ExpenseManager;
import seedu.tripbuddy.framework.Ui;
import seedu.tripbuddy.storage.DataHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TripBuddy {

    private static final String LOG_PATH = "log.txt";
    private static final String FILE_PATH = "tripbuddy_data.json";
    private static final int DEFAULT_BUDGET = 1000;

    /**
     * Directs logging to a file
     */
    private static void initLogging() {
        Logger logger = Logger.getLogger("TripBuddy");
        logger.setUseParentHandlers(false);
        try {
            FileHandler fh = new FileHandler(LOG_PATH);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * Runs the main program loop, loads all data, and handles user input.
     */
    public static void run() {
        initLogging();
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        try {
            DataHandler.loadData(FILE_PATH);
        } catch (JSONException e) {
            ExceptionHandler.handleJSONException(e);
        } catch (FileNotFoundException e) {
            ExceptionHandler.handleFileNotFoundException(e);
        }

        Ui.printStartMessage();
        while (true) {
            String userInput = Ui.getUserInput();

            if (userInput.isEmpty()) {
                continue;
            }

            if (InputHandler.isQuitCommand(userInput)) {
                try {
                    DataHandler.saveData(FILE_PATH);
                } catch (IOException e) {
                    ExceptionHandler.handleException(e);
                }
                Ui.printEndMessage();
                return;
            }

            InputHandler.handleUserInput(userInput);
        }
    }

    public static void main(String[] args) {
        TripBuddy.run();
    }
}
