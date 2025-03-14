package seedu.tripbuddy.command;

import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.exception.InvalidKeywordException;
import seedu.tripbuddy.framework.CommandHandler;
import seedu.tripbuddy.framework.Ui;

/**
 * Handles parsing of user commands and delegates the execution of tasks.
 * This class is responsible for interpreting user input and calling
 * appropriate methods in other classes to execute commands.
 */
public class Parser {

    private final CommandHandler cmdRunner;
    private final ExceptionHandler exceptionHandler;
    private final Ui ui;

    public Parser(Ui ui) {
        this.cmdRunner = new CommandHandler();
        this.exceptionHandler = new ExceptionHandler(ui);
        this.ui = ui;
    }

    public boolean isQuitCommand(String userInput) {
        return userInput.equals("quit");
    }

    public void handleUserInput(String userInput) {
        String[] tokens = userInput.strip().split(" ");
        String keyword = tokens[0].toLowerCase();
        try {
            String message = switch (keyword) {
            case "tutorial" -> cmdRunner.handleTutorial();
            case "set-budget" -> cmdRunner.handleSetBudget(Integer.parseInt(tokens[1]));
            case "view-budget" -> cmdRunner.handleViewBudget();
            case "set-category" -> cmdRunner.handleSetCategory(tokens[1], tokens[2]);
            case "delete-expense" -> cmdRunner.handleDeleteExpense(tokens[1]);
            default -> throw new InvalidKeywordException(keyword);
            };
            ui.printMessage(message);
        } catch (InvalidKeywordException e) {
            exceptionHandler.handleInvalidKeywordException(e);
        } catch (NumberFormatException e) {
            exceptionHandler.handleNumberFormatException(e);
        } catch (InvalidArgumentException e) {
            exceptionHandler.handleInvalidArgumentException(e);
        }
    }
}
