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

    public static boolean isQuitCommand(String userInput) {
        return userInput.split(" ")[0].equals("quit");
    }

    public static void handleUserInput(String userInput) {
        String[] tokens = userInput.strip().split(" ");
        String keyword = tokens[0].toLowerCase();
        try {
            String message = switch (keyword) {
            case "tutorial" -> CommandHandler.handleTutorial();
            case "set-budget" -> CommandHandler.handleSetBudget(Integer.parseInt(tokens[1]));
            case "adjust-budget" -> CommandHandler.handleAdjustBudget(Integer.parseInt(tokens[1]));
            case "view-budget" -> CommandHandler.handleViewBudget();
            case "create-category" -> CommandHandler.handleCreateCategory(tokens[1]);
            case "set-category" -> CommandHandler.handleSetCategory(tokens[1], tokens[2]);
            case "add-expense" -> {
                if (tokens.length == 4) {
                    yield CommandHandler.handleAddExpense(tokens[1], Integer.parseInt(tokens[2]), tokens[3]);
                } else if (tokens.length > 4) {
                    yield CommandHandler.handleAddExpense(tokens[1], Integer.parseInt(tokens[2]), tokens[3], tokens[4]);
                } else if (tokens.length == 3) {
                    yield CommandHandler.handleAddExpense(tokens[1], Integer.parseInt(tokens[2]));
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            case "delete-expense" -> CommandHandler.handleDeleteExpense(tokens[1]);
            case "list-expense" -> CommandHandler.handleListExpense(tokens.length == 1? null : tokens[1]);
            case "view-history" -> CommandHandler.handleViewHistory();
            case "max-expense" -> CommandHandler.handleMaxExpense();
            case "min-expense" -> CommandHandler.handleMinExpense();
            case "filter-date" -> {
                if (tokens.length >= 5) {
                    yield CommandHandler.handleFilterExpenseByDateRange(tokens[1] + " " + tokens[2],
                            tokens[3] + " " + tokens[4]);
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            case "view-currency" -> CommandHandler.handleViewCurrency();
            case "search" -> CommandHandler.handleSearch(tokens[1]);
            case "view-categories" -> CommandHandler.handleViewCategories();
            case "clear" -> CommandHandler.handleClearAll();
            default -> throw new InvalidKeywordException(keyword);
            };

            Ui.printMessage(message);
        } catch (InvalidKeywordException e) {
            ExceptionHandler.handleInvalidKeywordException(e);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleNumberFormatException();
        } catch (InvalidArgumentException e) {
            ExceptionHandler.handleInvalidArgumentException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            ExceptionHandler.handleArrayIndexOutOfBoundsException();
        }
    }
}
