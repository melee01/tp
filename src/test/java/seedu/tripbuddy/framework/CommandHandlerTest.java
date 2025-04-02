package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.exception.InvalidArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandHandlerTest {

    final int DEFAULT_BUDGET = 2333;

    @Test
    void addExpense_a1() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        String message = CommandHandler.handleAddExpense("a", 1);
        assertEquals("Expense a added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", DEFAULT_BUDGET - 1.) + ".",
                message);
    }

    @Test
    void addExpense_negativeAmount_expectInvalidArgumentException() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class,
                () -> CommandHandler.handleAddExpense("a", -1));
    }

    @Test
    void setBudget_set135() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        String message = CommandHandler.handleSetBudget(135);
        assertEquals("Your budget has been set to $" + String.format("%.2f", 135.) + ".",
                message);
    }
}
