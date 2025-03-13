package seedu.trip_buddy.command;

import seedu.trip_buddy.TripBuddy;
import seedu.trip_buddy.ui.Ui;

/**
 * Handles parsing of user commands and delegates the execution of tasks.
 * This class is responsible for interpreting user input and calling
 * appropriate methods in other classes to execute commands.
 */
public class Parser {
    private TripBuddy tripBuddy;
    private Ui ui;

    public Parser() {
        this.tripBuddy = new TripBuddy();
        this.ui = new Ui();
    }

    public Parser(Ui ui, TripBuddy tripBuddy) {
        this.tripBuddy = tripBuddy;
        this.ui = ui;
    }

    /**
     * Checks whether the given user input is valid for continuing the program.
     * The input is considered invalid if it equals "quit", which is the
     * command to exit the program.
     *
     * @param userInput A string entered by the user.
     * @return {@code true} if the input is not an exit command, {@code false} otherwise.
     */
    public boolean isValidUserInput(String userInput) {
        return !userInput.equals("quit");
    }

    public void handleCommand(String userInput) {
        String[] tokens = userInput.split(" ");
        String command = tokens[0].toLowerCase();
        try {
            switch (command) {
            case "tutorial":
                tripBuddy.handleTutorial();
                break;
            case "set-budget":
                tripBuddy.handleSetBudget(tokens);
                break;
            default:
                ui.printMessage("Invalid command. Please review the user guide for more information.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Uh oh! Invalid number format. Please enter a valid integer.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Uh oh! Missing arguments for command. Please review the user guide.");
        }

    }
}
