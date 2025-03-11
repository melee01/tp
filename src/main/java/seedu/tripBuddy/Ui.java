package seedu.tripBuddy;

import java.util.Scanner;

/**
 * Handles user interaction, including printing messages and getting user input.
 * This class manages all input/output operations for the program.
 */
public class Ui {
    private final Scanner scanner;
    static final String LINE = "____________________________________________________________";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints a line separator for better readability.
     */
    public void printLineSeparator() {
        System.out.println(LINE);
    }

    /**
     * Prints a start message when the program begins.
     */
    public void printStartMessage() {
        printLineSeparator();
        System.out.println("Welcome to TripBuddy! Type `tutorial` for a list of available commands.");
        printLineSeparator();
    }

    /**
     * Retrieves user input from the console.
     *
     * @return The command entered by the user.
     */
    public String getUserInput() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message.
     */
    public void showError() {
        System.out.println("Something went wrong. Please review the user guide for formatting specifics.");
    }

    /**
     * Prints a message indicating that the program is exiting.
     */
    public void printEndMessage() {
        printLineSeparator();
        System.out.println("Your TripBuddy session has ended. Bye!");
        printLineSeparator();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}