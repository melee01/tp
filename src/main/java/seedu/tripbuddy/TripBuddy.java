package seedu.tripbuddy;

import seedu.tripbuddy.command.Parser;
import seedu.tripbuddy.framework.Ui;

public class TripBuddy {

    private final Ui ui;
    private final Parser parser;

    public TripBuddy() {
        this.ui = new Ui();
        this.parser = new Parser(ui);
    }

    /**
     * Runs the main program loop, handling user input and executing commands.
     * The program starts by printing a welcome message and then enters a loop
     * where it continuously reads user input, processes commands via the parser,
     * and prints responses until the user exits and an end message is displayed.
     */
    public void run() {
        ui.printStartMessage();
        while (true) {
            String userInput = ui.getUserInput();

            // Skip empty input
            if (userInput.isEmpty()) {
                continue;
            }

            // Quit command
            if (parser.isQuitCommand(userInput)) {
                ui.printEndMessage();
                return;
            }

            parser.handleUserInput(userInput);
        }
    }

    /**
     * Main entry-point for the TripBuddy application.
     */
    public static void main(String[] args) {
        new TripBuddy().run();
    }
}
