package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ExpenseManagerTest {

    @Test
    void initBudgetTest() {
        ExpenseManager.initExpenseManager(2333);
        assertEquals(2333, ExpenseManager.getBudget());
    }

    @Test
    void setBudgetTest() {
        ExpenseManager.initExpenseManager(2333);
        ExpenseManager.setBudget(233);
        assertEquals(233, ExpenseManager.getBudget());
    }

    @Test
    void addDeleteExpenseTest_add3Delete1() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(2333);
        ExpenseManager.addExpense("a", 1);
        ExpenseManager.addExpense("b", 2);
        ExpenseManager.deleteExpense(1);
        ExpenseManager.addExpense("c", 3);

        List<Expense> expenses = ExpenseManager.getExpenses();
        for (Expense expense : expenses) {
            System.err.println(expense);
        }
        assertEquals(2, expenses.size());
    }

    @Test
    void addDeleteTest_invalidDeleteID_expectInvalidArgumentException() {
        ExpenseManager.initExpenseManager(2333);
        ExpenseManager.addExpense("a", 1);
        ExpenseManager.addExpense("b", 2);
        assertThrows(InvalidArgumentException.class, () -> ExpenseManager.deleteExpense(3));
    }

    @Test
    void addExpenseTest_categoryNotExists() {
        ExpenseManager.initExpenseManager(2333);
        assertArrayEquals(new String[]{}, ExpenseManager.getCategories().toArray());

        ExpenseManager.addExpense("lunch", 100, "food");
        System.err.println(Arrays.toString(ExpenseManager.getCategories().toArray()));
        assertArrayEquals(new String[]{"food"}, ExpenseManager.getCategories().toArray(), "1");

        ExpenseManager.addExpense("lol", 233);
        System.err.println(Arrays.toString(ExpenseManager.getCategories().toArray()));
        assertArrayEquals(new String[]{"food"}, ExpenseManager.getCategories().toArray(), "2");

        ExpenseManager.addExpense("a", 10, "sth");
        System.err.println(Arrays.toString(ExpenseManager.getCategories().toArray()));
        assertEquals(2, ExpenseManager.getCategories().size(), "3");

        for (Expense expense : ExpenseManager.getExpenses()) {
            System.err.println(expense);
        }
        assertEquals(3, ExpenseManager.getExpenses().size(), "4");
    }

    @Test
    void getMaxExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(1000);
        ExpenseManager.addExpense("expense1", 100);
        ExpenseManager.addExpense("expense2", 300);
        ExpenseManager.addExpense("expense3", 200);

        Expense maxExpense = ExpenseManager.getMaxExpense();
        // Expect "expense2" with amount 300.00
        assertEquals("expense2", maxExpense.getName());
        assertEquals(300.00, maxExpense.getAmount(), 0.001);
    }

    @Test
    void getMinExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(1000);
        ExpenseManager.addExpense("expense1", 100);
        ExpenseManager.addExpense("expense2", 300);
        ExpenseManager.addExpense("expense3", 200);

        Expense minExpense = ExpenseManager.getMinExpense();
        // Expect "expense1" with amount 100.00
        assertEquals("expense1", minExpense.getName());
        assertEquals(100.00, minExpense.getAmount(), 0.001);
    }

    @Test
    void getMaxExpense_emptyExpenses_throwsException() {
        ExpenseManager.initExpenseManager(1000);
        assertThrows(InvalidArgumentException.class, () -> ExpenseManager.getMaxExpense());
    }

    @Test
    void getMinExpense_emptyExpenses_throwsException() {
        ExpenseManager.initExpenseManager(1000);
        assertThrows(InvalidArgumentException.class, () -> ExpenseManager.getMinExpense());
    }
}
