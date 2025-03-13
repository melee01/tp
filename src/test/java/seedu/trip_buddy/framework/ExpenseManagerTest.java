package seedu.trip_buddy.framework;

import org.junit.jupiter.api.Test;
import seedu.trip_buddy.dataclass.Expense;

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
