package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.time.LocalDate;

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
        assertEquals("Your budget has been set to $135,00.",
                message);
    }

    @Test
    void handleMaxExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("a", 10);
        ExpenseManager.addExpense("b", 20);

        Expense expense = new Expense("b", 20);
        String expected = "Maximum expense: " +  expense.toString();
        String actual = CommandHandler.handleMaxExpense();
        assertEquals(expected, actual);
    }

    @Test
    void handleMinExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("a", 10);
        ExpenseManager.addExpense("b", 20);

        Expense expense = new Expense("a", 10);
        String expected = "Minimum expense: " +  expense.toString();
        String actual = CommandHandler.handleMinExpense();
        assertEquals(expected, actual);
    }

    @Test
    void handleMaxExpense_noExpenses_throwsException() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class, () -> CommandHandler.handleMaxExpense());
    }

    @Test
    void handleMinExpense_noExpenses_throwsException() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class, () -> CommandHandler.handleMinExpense());
    }

    @Test
    void handleListExpense_totalAmountSpent() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("a", 50);
        ExpenseManager.addExpense("b", 100);

        Expense expense2 = new Expense("b", 100);
        Expense expense1 = new Expense("a", 50);

        String expected = "Expense list is: " +
                "\n - " + expense1.toString() +
                "\n - " + expense2.toString() +
                "\nTotal amount spent: $150,00.";
        String actual = CommandHandler.handleListExpense(null);
        assertEquals(expected, actual);
    }
}
