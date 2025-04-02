package seedu.tripbuddy.framework;

import java.util.Scanner;

/**
 * Handles user interaction, including printing messages and getting user input.
 * This class manages all input/output operations for the program.
 */
public class Ui {

    private static final String LINE =
            "____________________________________________________________";
    private static final String START_MESSAGE =
            "Welcome to TripBuddy! Type `tutorial` for a list of available commands.";
    private static final String END_MESSAGE = "Your TripBuddy session has ended. Bye!";

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a line separator for better readability.
     */
    public static void printLineSeparator() {
        System.out.println(LINE);
    }

    /**
     * Prints a start message when the program begins.
     */
    public static void printStartMessage() {
        printLineSeparator();
        System.out.println(START_MESSAGE);
        printLineSeparator();
    }

    /**
     * Retrieves user input from the console.
     *
     * @return The command entered by the user, whitespace stripped.
     */
    public static String getUserInput() {
        return scanner.nextLine().strip();
    }

    /**
     * Prints a message indicating that the program is exiting.
     */
    public static void printEndMessage() {
        printLineSeparator();
        System.out.println(END_MESSAGE);
        printLineSeparator();
    }

    public static void printMessage(String message) {
        printLineSeparator();
        System.out.println(message);
        printLineSeparator();
    }
}
