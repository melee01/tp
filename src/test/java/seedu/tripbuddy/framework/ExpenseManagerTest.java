package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        ExpenseManager.deleteExpense("a");
        ExpenseManager.addExpense("c", 3);

        List<Expense> expenses = ExpenseManager.getExpenses();
        for (Expense expense : expenses) {
            System.err.println(expense);
        }
        assertEquals(2, expenses.size());
    }

    @Test
    void addExpenseTest_categoryNotExists() throws InvalidArgumentException {
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
    void setExpenseCategoryTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(200);
        ExpenseManager.addExpense("testExpense", 50);
        ExpenseManager.setExpenseCategory("testExpense", "utilities");
        Expense expense = ExpenseManager.getExpense(0);
        assertEquals("utilities", expense.getCategory());
    }

    @Test
    void getMaxExpenseTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(1000);
        ExpenseManager.addExpense("expense1", 100);
        ExpenseManager.addExpense("expense2", 300);
        ExpenseManager.addExpense("expense3", 200);

        Expense maxExpense = ExpenseManager.getMaxExpense();
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

    @Test
    void getExpensesByDateRangeTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(1000);

        ExpenseManager.addExpense("expense1", 100);
        ExpenseManager.addExpense("expense2", 200);
        ExpenseManager.addExpense("expense3", 300);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ExpenseManager.getExpenses().get(0).setDateTime(LocalDateTime.parse("2025-04-01 10:00:00", formatter));
        ExpenseManager.getExpenses().get(1).setDateTime(LocalDateTime.parse("2025-04-03 15:30:00", formatter));
        ExpenseManager.getExpenses().get(2).setDateTime(LocalDateTime.parse("2025-04-05 20:00:00", formatter));

        LocalDateTime start = LocalDateTime.parse("2025-04-01 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2025-04-04 23:59:59", formatter);

        List<Expense> filteredExpenses = ExpenseManager.getExpensesByDateRange(start, end);

        assertEquals(2, filteredExpenses.size(), "Should return 2 expenses within the date range");

        boolean containsExp1 = filteredExpenses.stream().anyMatch(exp -> exp.getName().equals("expense1"));
        boolean containsExp2 = filteredExpenses.stream().anyMatch(exp -> exp.getName().equals("expense2"));
        boolean containsExp3 = filteredExpenses.stream().anyMatch(exp -> exp.getName().equals("expense3"));

        assertTrue(containsExp1, "Filtered expenses should contain expense1");
        assertTrue(containsExp2, "Filtered expenses should contain expense2");
        assertFalse(containsExp3, "Filtered expenses should not contain expense3");
    }

    @Test
    void getExpensesByDateRangeEmptyResultTest() throws InvalidArgumentException {
        ExpenseManager.initExpenseManager(1000);

        ExpenseManager.addExpense("expenseA", 100);
        ExpenseManager.addExpense("expenseB", 200);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ExpenseManager.getExpenses().get(0).setDateTime(LocalDateTime.parse("2025-03-25 09:00:00", formatter));
        ExpenseManager.getExpenses().get(1).setDateTime(LocalDateTime.parse("2025-03-30 18:00:00", formatter));

        LocalDateTime start = LocalDateTime.parse("2025-04-01 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2025-04-05 23:59:59", formatter);

        List<Expense> filteredExpenses = ExpenseManager.getExpensesByDateRange(start, end);

        assertEquals(0, filteredExpenses.size(), "Should return 0 expenses since none are within the date range");
    }
}
