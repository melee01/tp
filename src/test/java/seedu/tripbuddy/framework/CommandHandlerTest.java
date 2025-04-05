package seedu.tripbuddy.framework;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.dataclass.Currency;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;


class CommandHandlerTest {

    static final Logger LOGGER = Logger.getLogger(CommandHandlerTest.class.getName());
    static final int DEFAULT_BUDGET = 2333;

    @Test
    void addExpense_a1() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        String message = CommandHandler.handleAddExpense("a", 1);
        assertEquals("Expense a added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", DEFAULT_BUDGET - 1.) + " SGD.",
                message);
    }

    @Test
    void addExpense_negativeAmount_expectInvalidArgumentException() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(AssertionError.class,
                () -> CommandHandler.handleAddExpense("a", -1));
    }

    @Test
    void addExpense_incorrectCurrency() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class,
                () -> CommandHandler.handleAddExpense("a", 20, "dinner", "XXX"));

    }

    @Test
    void addExpense_correct() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        try {
            CommandHandler.handleAddExpense("a", 20, "dinner", "USD");
            Expense expense = ExpenseManager.getExpense(0);
            assertEquals(expense.getAmount(), Currency.USD.convert(20));

        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    void setBudget_set135() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        String message = CommandHandler.handleSetBudget(135);
        assertEquals("Your budget has been set to 135.00 SGD.", message);
    }

    @Test
    void handleViewBudgetTest_positiveRemaining() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(100);
        ExpenseManager.addExpense("item1", 40);
        String message = CommandHandler.handleViewBudget();
        assertTrue(message.contains("remaining budget of 60.00 SGD."));
    }

    @Test
    void handleViewBudgetTest_exceededBudget() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(100);
        ExpenseManager.addExpense("item1", 150);
        String message = CommandHandler.handleViewBudget();
        LOGGER.log(Level.INFO, message);
        assertTrue(message.contains("exceeded your budget by 50"));
    }

    @Test
    void handleViewHistoryTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(200);
        ExpenseManager.addExpense("expense1", 20);
        ExpenseManager.addExpense("expense2", 30);
        String history = CommandHandler.handleViewHistory();
        assertTrue(history.contains("expense1"));
        assertTrue(history.contains("expense2"));
    }

    @Test
    void handleCreateCategoryTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        String message = CommandHandler.handleCreateCategory("food");
        assertEquals("Successfully created category: food.", message);
        assertTrue(ExpenseManager.getCategories().contains("food"));
    }

    @Test
    void handleSetCategoryTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("meal", 50);
        String message = CommandHandler.handleSetCategory("meal", "dining");
        assertEquals("Successfully set category for meal to dining.", message);
        assertEquals("dining", ExpenseManager.getExpense(0).getCategory());
    }

    @Test
    void handleDeleteExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        ExpenseManager.addExpense("deleteTest", 20);
        String message = CommandHandler.handleDeleteExpense("deleteTest");
        assertTrue(message.contains("Expense deleteTest deleted successfully."));
        assertEquals(0, ExpenseManager.getExpenses().size());
    }

    @Test
    void handleAdjustBudgetTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(200);
        String message = CommandHandler.handleAdjustBudget(300);
        assertTrue(message.contains("Your budget has been updated to $300.0."));
        assertTrue(message.contains("You have $300.00 remaining to spend!"));
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
        assertThrows(InvalidArgumentException.class, CommandHandler::handleMaxExpense);
    }

    @Test
    void handleMinExpense_noExpenses_throwsException() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class, CommandHandler::handleMinExpense);
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
                "\nTotal amount spent: 150.00 SGD.";
        String actual = CommandHandler.handleListExpense(null);
        assertEquals(expected, actual);
    }

    @Test
    void handleSearchExpense_matchingExpenses() throws InvalidArgumentException {
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
    void handleSearchExpense_noMatchingExpenses() throws InvalidArgumentException {
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
    public void getCategories_returnCorrectList() throws InvalidArgumentException {
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
    public void clear_returnsEmptyList() throws InvalidArgumentException {
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

    @Test
    public void viewCurrency_returnsEmptyList() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertNotEquals(null, CommandHandler.handleViewCurrency());
    }

    @Test
    public void setBaseCurrency_correct() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        try{
            CommandHandler.handleSetBaseCurrency("USD");
        } catch (InvalidArgumentException e) {
            fail();
        }

        assertEquals(Currency.USD.getRate(), 1);
        assertNotEquals(Currency.SGD.getRate(), 1);

    }

    @Test
    public void setBaseCurrency_incorrect() {
        ExpenseManager.initExpenseManager(DEFAULT_BUDGET);
        assertThrows(InvalidArgumentException.class, () -> CommandHandler.handleSetBaseCurrency("XXX"));
    }
}
