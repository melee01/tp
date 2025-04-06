package seedu.tripbuddy.framework;

import seedu.tripbuddy.command.Command;
import seedu.tripbuddy.command.Keyword;
import seedu.tripbuddy.command.Parser;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.exception.InvalidKeywordException;
import seedu.tripbuddy.exception.MissingOptionException;

import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

/**
 * Handles parsing of user commands and delegates the execution of tasks.
 * This class is responsible for interpreting user input and calling
 * appropriate methods in other classes to execute commands.
 */
public class InputHandler {

    private static InputHandler instance = null;

    private final CommandHandler commandHandler;
    private final Parser parser;

    private InputHandler(Logger logger) {
        this.commandHandler = CommandHandler.getInstance();
        this.parser = Parser.getInstance(logger);
    }

    public static InputHandler getInstance(Logger logger) {
        if (instance == null) {
            instance = new InputHandler(logger);
        }
        return instance;
    }

    public boolean isQuitCommand(String userInput) {
        return userInput.split(" ")[0].equals("quit");
    }

    public String handleUserInput(String userInput) {
        try {
            Command cmd = parser.parseCommand(userInput);
            Keyword keyword = cmd.getKeyword();
            int optCount = cmd.getOptCount();
            return switch (keyword) {
            case TUTORIAL -> commandHandler.handleTutorial();
            case SET_BUDGET -> commandHandler.handleSetBudget(cmd.parseDouble(""));
            case VIEW_BUDGET -> commandHandler.handleViewBudget();
            case CREATE_CATEGORY -> commandHandler.handleCreateCategory(cmd.getOpt(""));
            case SET_CATEGORY -> commandHandler.handleSetCategory(cmd.getOpt(""), cmd.getOpt("c"));
            case ADD_EXPENSE -> {
                double amount = cmd.parseDouble("a");
                if (cmd.hasOpt("c")) {
                    yield commandHandler.handleAddExpense(cmd.getOpt(""), amount, cmd.getOpt("c"));
                }
                yield commandHandler.handleAddExpense(cmd.getOpt(""), amount);
            }
            case DELETE_EXPENSE -> commandHandler.handleDeleteExpense(cmd.getOpt(""));
            case LIST_EXPENSE -> commandHandler.handleListExpense(optCount == 0 ? null : cmd.getOpt(""));
            case MAX_EXPENSE -> commandHandler.handleMaxExpense();
            case MIN_EXPENSE -> commandHandler.handleMinExpense();
            case FILTER_DATE -> commandHandler.handleFilterExpenseByDateRange(cmd.getOpt("f"), cmd.getOpt("t"));
            case VIEW_CURRENCY -> commandHandler.handleViewCurrency();
            case SEARCH -> commandHandler.handleSearch(cmd.getOpt(""));
            case VIEW_CATEGORIES -> commandHandler.handleViewCategories();
            case SET_BASE_CURRENCY -> commandHandler.handleSetBaseCurrency(cmd.getOpt(""));
            case CLEAR -> commandHandler.handleClearAll();
            };
        } catch (DateTimeParseException e) {
            return ExceptionHandler.handleDateTimeParseException(e);
        } catch (InvalidKeywordException e) {
            return ExceptionHandler.handleInvalidKeywordException(e);
        } catch (MissingOptionException e) {
            return ExceptionHandler.handleMissingOptionException(e);
        } catch (NumberFormatException e) {
            return ExceptionHandler.handleNumberFormatException();
        } catch (InvalidArgumentException e) {
            return ExceptionHandler.handleInvalidArgumentException(e);
        }
    }
}
