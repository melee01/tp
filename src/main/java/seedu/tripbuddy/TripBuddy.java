package seedu.tripbuddy;

import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.framework.InputHandler;
import seedu.tripbuddy.framework.ExpenseManager;
import seedu.tripbuddy.framework.Ui;
import seedu.tripbuddy.storage.DataHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TripBuddy {

    private static final String FILE_PATH = "tripbuddy_data.json";
    private static final int DEFAULT_BUDGET = 1000;

    /**
     * Runs the main program loop, loads all data, and handles user input.
     */
    public static void run() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        try {
            DataHandler.loadData(FILE_PATH);
        } catch (FileNotFoundException e) {
            ExceptionHandler.handleException(e);
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
