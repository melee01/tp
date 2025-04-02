package seedu.tripbuddy;

import seedu.tripbuddy.framework.InputHandler;
import seedu.tripbuddy.framework.ExpenseManager;
import seedu.tripbuddy.framework.Ui;

public class TripBuddy {

    private static final int DEFAULT_BUDGET = 1000;

    /**
     * Runs the main program loop, handling user input and executing commands.
     */
    public static void run() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        Ui.printStartMessage();
        while (true) {
            String userInput = Ui.getUserInput();

            if (userInput.isEmpty()) {
                continue;
            }

            if (InputHandler.isQuitCommand(userInput)) {
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
