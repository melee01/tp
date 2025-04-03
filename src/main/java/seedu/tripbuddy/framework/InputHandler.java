package seedu.tripbuddy.framework;

import seedu.tripbuddy.command.Command;
import seedu.tripbuddy.command.Keyword;
import seedu.tripbuddy.command.Option;
import seedu.tripbuddy.command.Parser;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.exception.InvalidKeywordException;
import seedu.tripbuddy.exception.MissingOptionException;

import java.util.ArrayList;

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
                case SET_BUDGET -> CommandHandler.handleSetBudget(Double.parseDouble(cmd.getOpt("")));
                case ADJUST_BUDGET -> CommandHandler.handleAdjustBudget(Double.parseDouble(cmd.getOpt("")));
                case VIEW_BUDGET -> CommandHandler.handleViewBudget();
                case CREATE_CATEGORY -> CommandHandler.handleCreateCategory(cmd.getOpt(""));
                case SET_CATEGORY -> CommandHandler.handleSetCategory(cmd.getOpt(""), cmd.getOpt("c"));
                case ADD_EXPENSE -> {
                    double amount = Double.parseDouble(cmd.getOpt("a"));
                    // TODO: implement currency
                    if (cmd.hasOpt("c")) {
                        yield CommandHandler.handleAddExpense(cmd.getOpt(""), amount, cmd.getOpt("c"));
                    }
                    yield CommandHandler.handleAddExpense(cmd.getOpt(""), amount);
                }
                case DELETE_EXPENSE -> CommandHandler.handleDeleteExpense(cmd.getOpt(""));
                case LIST_EXPENSE -> CommandHandler.handleListExpense(optCount == 0 ? null : cmd.getOpt(""));
                case VIEW_HISTORY -> CommandHandler.handleViewHistory();
            };
            Ui.printMessage(message);
        } catch (InvalidKeywordException e) {
            ExceptionHandler.handleInvalidKeywordException(e);
        } catch (MissingOptionException e) {
            ExceptionHandler.handleMissingOptionException(e);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleNumberFormatException();
        } catch (InvalidArgumentException e) {
            ExceptionHandler.handleInvalidArgumentException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            ExceptionHandler.handleArrayIndexOutOfBoundsException();
        }
    }
}
