package seedu.trip_buddy;

import seedu.trip_buddy.command.Parser;
import seedu.trip_buddy.framework.Ui;

public class TripBuddy {
    private int budget;
    private Ui ui;
    private Parser parser;

    public TripBuddy() {
        this.budget = 0;
        this.ui = new Ui();
        this.parser = new Parser(ui, this);
    }

    /**
     * Runs the main program loop, handling user input and executing commands.
     * The program starts by printing a welcome message and then enters a loop
     * where it continuously reads user input, processes commands via the parser,
     * and prints responses until the user exits and an end message is displayed.
     */
    public void run() {
        ui.printStartMessage();
        String userInput = ui.getUserInput();
        while (parser.isValidUserInput(userInput)) {
            ui.printLineSeparator();
            parser.handleCommand(userInput);
            ui.printLineSeparator();
            userInput = ui.getUserInput();
        }
        ui.printEndMessage();
    }

    public void handleTutorial() {
        ui.printMessage(
                "Welcome to the tutorial of TripBuddy!\n\n" +
                        "Note on format: [] means optional!\n\n" +
                        "Here are the commands you can use:\n" +
                        "1. set-budget AMOUNT - Set your total trip budget.\n" +
                        "2. add-expense EXPENSE_NAME AMOUNT [CATEGORY] - Add a new expense.\n" +
                        "3. delete-expense EXPENSE_NAME - Remove an expense by name.\n" +
                        "4. create-category CATEGORY - Create a new expense category.\n" +
                        "5. set-category EXPENSE_NAME CATEGORY - Assign an expense to a category.\n" +
                        "6. view-budget - Check your remaining budget.\n" +
                        "7. list-expense [CATEGORY] - Calculate sum of recorded expenses.\n" +
                        "8. view-history - See a history of all expenses made.\n\n" +
                        "Enjoy tracking your expenses with TripBuddy!"
        );
    }

    public void handleSetBudget(String[] tokens) {
        this.budget = Integer.parseInt(tokens[1]);
        ui.printMessage("Your budget has been set to $" + this.budget + ".");
    }

    /**
     * Main entry-point for the TripBuddy application.
     */
    public static void main(String[] args) {
        new TripBuddy().run();
    }
}
