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
        return userInput.split(" ")[0].equals("quit");
    }

    public void handleUserInput(String userInput) {
        String[] tokens = userInput.strip().split(" ");
        String keyword = tokens[0].toLowerCase();
        try {
            String message = switch (keyword) {
            case "tutorial" -> cmdRunner.handleTutorial();
            case "set-budget" -> cmdRunner.handleSetBudget(Integer.parseInt(tokens[1]));
            case "adjust-budget" -> cmdRunner.handleAdjustBudget(Integer.parseInt(tokens[1]));
            case "view-budget" -> cmdRunner.handleViewBudget();
            case "create-category" -> cmdRunner.handleCreateCategory(tokens[1]);
            case "set-category" -> cmdRunner.handleSetCategory(tokens[1], tokens[2]);
            case "add-expense" -> {
                if (tokens.length >= 4) {
                    yield cmdRunner.handleAddExpense(tokens[1], Integer.parseInt(tokens[2]), tokens[3]);
                } if (tokens.length == 3) {
                    yield cmdRunner.handleAddExpense(tokens[1], Integer.parseInt(tokens[2]));
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            case "delete-expense" -> cmdRunner.handleDeleteExpense(tokens[1]);
            case "list-expense" -> cmdRunner.handleListExpense(tokens.length == 1? null : tokens[1]);
            case "view-history" -> cmdRunner.handleViewHistory();
            default -> throw new InvalidKeywordException(keyword);
            };
            ui.printMessage(message);
        } catch (InvalidKeywordException e) {
            exceptionHandler.handleInvalidKeywordException(e);
        } catch (NumberFormatException e) {
            exceptionHandler.handleNumberFormatException();
        } catch (InvalidArgumentException e) {
            exceptionHandler.handleInvalidArgumentException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionHandler.handleArrayIndexOutOfBoundsException();
        }
    }
}
