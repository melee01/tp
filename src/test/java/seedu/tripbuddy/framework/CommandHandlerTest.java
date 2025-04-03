package seedu.tripbuddy.framework;

import java.util.List;
import org.junit.jupiter.api.Test;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("Your budget has been set to $135.00.",
                message);
    }

    @Test
    void handleMaxExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("a", 10);
        ExpenseManager.addExpense("b", 20);

        Expense expense = new Expense("b", 20);
        String expected = "Maximum expense: " +  expense;
        String actual = CommandHandler.handleMaxExpense();
        assertEquals(expected, actual);
    }

    @Test
    void handleMinExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("a", 10);
        ExpenseManager.addExpense("b", 20);

        Expense expense = new Expense("a", 10);
        String expected = "Minimum expense: " +  expense;
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

        String expected = "Here is a list of your past expenses: " +
                "\n - " + expense1 +
                "\n - " + expense2 +
                "\nTotal amount spent: $150.00.";
        String actual = CommandHandler.handleListExpense(null);
        assertEquals(expected, actual);
    }

    @Test
    void handleSearchExpense_matchingExpenses() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("lunch", 20);
        ExpenseManager.addExpense("dinner", 40);
        ExpenseManager.addExpense("lunch-buffet", 30);
        ExpenseManager.addExpense("transport", 15);

        Expense expense1 = new Expense("lunch", 20);
        Expense expense2 = new Expense("lunch-buffet", 30);

        String expected = "Expenses that matched your search word 'lunch':" +
                "\n - " + expense1 +
                "\n - " + expense2;
        String actual = CommandHandler.handleSearch("lunch");

        assertEquals(expected, actual);
    }

    @Test
    void handleSearchExpense_noMatchingExpenses() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("lunch", 20);
        ExpenseManager.addExpense("dinner", 40);
        ExpenseManager.addExpense("transport", 15);

        String expected = "There are no expenses that matched your search word: shopping.";
        String actual = CommandHandler.handleSearch("shopping");

        assertEquals(expected, actual);
    }

    @Test
    void handleSearchExpense_emptyExpenseList() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);

        String expected = "There are no expenses that matched your search word: shirt.";
        String actual = CommandHandler.handleSearch("shirt");

        assertEquals(expected, actual);
    }

    @Test
    public void getCategories_returnCorrectList() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("lunch", 20, "food");
        ExpenseManager.addExpense("dinner", 40, "food");
        ExpenseManager.addExpense("grab", 15, "transport");

        List<String> categories = ExpenseManager.getCategories();

        assertEquals(2, categories.size());

        assertTrue(categories.contains("food"));
        assertTrue(categories.contains("transport"));
    }

    @Test
    public void getCategories_emptySet_returnsEmptyList() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        List<String> categories = ExpenseManager.getCategories();
        assertEquals(0, categories.size());
    }

    @Test
    public void clear_returnsEmptyList() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("greek-meal", 20, "food");
        ExpenseManager.addExpense("mediterranean-meal", 30, "food");
        ExpenseManager.addExpense("grab", 15, "transport");

        CommandHandler.handleClearAll();
        List<String> categories = ExpenseManager.getCategories();
        List<Expense> expenses = ExpenseManager.getExpenses();

        assertEquals(0, categories.size());
        assertEquals(0, expenses.size());
    }
}
