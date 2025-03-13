package seedu.trip_buddy.framework;

import java.util.Scanner;

/**
 * Handles user interaction, including printing messages and getting user input.
 * This class manages all input/output operations for the program.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________";
    public static final String START_MESSAGE = "Welcome to TripBuddy! Type `tutorial` for a list of available commands.";
    public static final String END_MESSAGE = "Your TripBuddy session has ended. Bye!";

    private final Scanner scanner;

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
        System.out.println(START_MESSAGE);
        printLineSeparator();
    }

    /**
     * Retrieves user input from the console.
     *
     * @return The command entered by the user, whitespace stripped.
     */
    public String getUserInput() {
        return scanner.nextLine().strip();
    }

    /**
     * Prints a message indicating that the program is exiting.
     */
    public void printEndMessage() {
        printLineSeparator();
        System.out.println(END_MESSAGE);
        printLineSeparator();
    }

    public void printMessage(String message) {
        printLineSeparator();
        System.out.println(message);
        printLineSeparator();
    }
}
