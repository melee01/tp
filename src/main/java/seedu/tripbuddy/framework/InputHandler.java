package seedu.tripbuddy.framework;

import seedu.tripbuddy.command.Command;
import seedu.tripbuddy.command.Keyword;
import seedu.tripbuddy.command.Parser;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.exception.InvalidKeywordException;
import seedu.tripbuddy.exception.MissingOptionException;

import java.time.format.DateTimeParseException;

/**
 * Handles parsing of user commands and delegates the execution of tasks.
 * This class is responsible for interpreting user input and calling
 * appropriate methods in other classes to execute commands.
 */
public class InputHandler {

    public static boolean isQuitCommand(String userInput) {
        return userInput.split(" ")[0].equals("quit");
    }

    public static void handleUserInput(String userInput) {
        try {
            Command cmd = Parser.parseCommand(userInput);
            Keyword keyword = cmd.getKeyword();
            int optCount = cmd.getOptCount();
            String message = switch (keyword) {
            case TUTORIAL -> CommandHandler.handleTutorial();
            case SET_BUDGET -> CommandHandler.handleSetBudget(cmd.parseDouble(""));
            case VIEW_BUDGET -> CommandHandler.handleViewBudget();
            case CREATE_CATEGORY -> CommandHandler.handleCreateCategory(cmd.getOpt(""));
            case SET_CATEGORY -> CommandHandler.handleSetCategory(cmd.getOpt(""), cmd.getOpt("c"));
            case ADD_EXPENSE -> {
                double amount = cmd.parseDouble("a");
                if (cmd.hasOpt("c")) {
                    yield CommandHandler.handleAddExpense(cmd.getOpt(""), amount, cmd.getOpt("c"));
                }
                yield CommandHandler.handleAddExpense(cmd.getOpt(""), amount);
            }
            case DELETE_EXPENSE -> CommandHandler.handleDeleteExpense(cmd.getOpt(""));
            case LIST_EXPENSE -> CommandHandler.handleListExpense(optCount == 0 ? null : cmd.getOpt(""));
            case MAX_EXPENSE -> CommandHandler.handleMaxExpense();
            case MIN_EXPENSE -> CommandHandler.handleMinExpense();
            case FILTER_DATE -> CommandHandler.handleFilterExpenseByDateRange(cmd.getOpt("f"), cmd.getOpt("t"));
            case VIEW_CURRENCY -> CommandHandler.handleViewCurrency();
            case SEARCH -> CommandHandler.handleSearch(cmd.getOpt(""));
            case VIEW_CATEGORIES -> CommandHandler.handleViewCategories();
            case SET_BASE_CURRENCY -> CommandHandler.handleSetBaseCurrency(cmd.getOpt(""));
            case CLEAR -> CommandHandler.handleClearAll();
            };
            Ui.printMessage(message);
        } catch (DateTimeParseException e) {
            ExceptionHandler.handleDateTimeParseException(e);
        } catch (InvalidKeywordException e) {
            ExceptionHandler.handleInvalidKeywordException(e);
        } catch (MissingOptionException e) {
            ExceptionHandler.handleMissingOptionException(e);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleNumberFormatException();
        } catch (InvalidArgumentException e) {
            ExceptionHandler.handleInvalidArgumentException(e);
        }
    }
}
