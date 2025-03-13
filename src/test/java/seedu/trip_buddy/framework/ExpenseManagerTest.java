package seedu.trip_buddy.framework;

import org.junit.jupiter.api.Test;
import seedu.trip_buddy.dataclass.Expense;
import seedu.trip_buddy.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseManagerTest {

    @Test
    void budgetTest() {
        ExpenseManager manager = new ExpenseManager(2333);
        assertEquals(2333, manager.getBudget(), "1");
        manager.setBudget(233);
        assertEquals(233, manager.getBudget(), "2");
    }

    @Test
    void addDeleteExpenseTest_add3Delete1() throws InvalidArgumentException {
        ExpenseManager manager = new ExpenseManager(2333);
        manager.addExpense("a", 1);
        manager.addExpense("b", 2);
        manager.deleteExpense(1);
        manager.addExpense("c", 3);

        List<Expense> expenses = manager.getExpenses();
        for (Expense expense : expenses) {
            System.err.println(expense);
        }
        assertEquals(2, expenses.size());
    }

    @Test
    void addDeleteTest_invalidDeleteID_expectInvalidArgumentException() {
        ExpenseManager manager = new ExpenseManager(2333);
        manager.addExpense("a", 1);
        manager.addExpense("b", 2);
        assertThrows(InvalidArgumentException.class, () -> manager.deleteExpense(3));
    }

    @Test
    void addExpenseTest_categoryNotExists() {
        ExpenseManager manager = new ExpenseManager(2333);
        assertArrayEquals(manager.getCategories().toArray(), new String[]{});

        manager.addExpense("lunch", 100, "food");
        System.err.println(Arrays.toString(manager.getCategories().toArray()));
        assertArrayEquals(new String[]{"food"}, manager.getCategories().toArray(), "1");

        manager.addExpense("lol", 233);
        System.err.println(Arrays.toString(manager.getCategories().toArray()));
        assertArrayEquals(new String[]{"food"}, manager.getCategories().toArray(), "2");

        manager.addExpense("a", 10, "sth");
        System.err.println(Arrays.toString(manager.getCategories().toArray()));
        assertEquals(2, manager.getCategories().size(), "3");

        for (Expense expense : manager.getExpenses()) {
            System.err.println(expense);
        }
        assertEquals(3, manager.getExpenses().size(), "4");
    }
}
