package seedu.tripbuddy.framework;

import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Has CRUD access to all user data.
 */
public class ExpenseManager {

    private static double budget;
    private static double totalExpense = 0;
    private static final HashSet<String> categories = new HashSet<>();
    private static final ArrayList<Expense> expenses = new ArrayList<>();

    /**
     * Clears all existing data and initializes budget value.
     */
    public static void initExpenseManager(double budget) {
        assert budget > 0 : "Budget must be positive";
        ExpenseManager.budget = budget;
        clearExpensesAndCategories();
    }

    public static double getBudget() {
        return budget;
    }

    public static double getTotalExpense() {
        return totalExpense;
    }

    public static void setTotalExpense(double totalExpense) {
        ExpenseManager.totalExpense = totalExpense;
    }

    public static double getRemainingBudget() {
        return budget - totalExpense;
    }

    public static List<String> getCategories() {
        return categories.stream().toList();
    }

    public static List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public static void clearExpensesAndCategories() {
        expenses.clear();
        categories.clear();
        totalExpense = 0;
    }

    public static void setBudget(double budget) {
        assert budget > 0 : "Budget must be positive";
        ExpenseManager.budget = budget;
    }

    /**
     * Adds a new category name into {@code categories} if not exists.
     */
    public static void createCategory(String categoryName) {
        categories.add(categoryName);
    }

    /**
     * Adds a new {@link Expense} without category.
     */
    public static void addExpense(String name, double amount) {
        assert amount > 0 : "Amount must be positive";
        Expense expense = new Expense(name, amount);
        expenses.add(expense);
        totalExpense += amount;
    }

    /**
     * Adds a new{@link Expense} with a specific category.
     * <ul>
     *     <li>A new category will be created if not exists.
     * </ul>
     */
    public static void addExpense(String name, double amount, String categoryName) {
        assert amount > 0 : "Amount must be positive";
        createCategory(categoryName);
        Expense expense = new Expense(name, amount, categoryName);
        expenses.add(expense);
        totalExpense += amount;
    }

    public static Expense getExpense(int id) throws InvalidArgumentException {
        if (id < 0 || id >= expenses.size()) {
            throw new InvalidArgumentException(Integer.toString(id));
        }
        return expenses.get(id);
    }

    public static void deleteExpense(int id) throws InvalidArgumentException {
        if (id < 0 || id >= expenses.size()) {
            throw new InvalidArgumentException(Integer.toString(id));
        }
        totalExpense -= expenses.get(id).getAmount();
        expenses.remove(id);
    }

    public static void deleteExpense(String expenseName) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expenses.remove(expense);
                totalExpense -= expense.getAmount();
                return;
            }
        }
        throw new InvalidArgumentException(expenseName);
    }

    public static List<Expense> getExpensesByCategory(String category) throws InvalidArgumentException {
        if (!categories.contains(category)) {
            throw new InvalidArgumentException(category);
        }

        ArrayList<Expense> ret = new ArrayList<>();
        for (Expense expense : expenses) {
            if (category.equals(expense.getCategory())) {
                ret.add(expense);
            }
        }
        return ret;
    }

    public static void setExpenseCategory(String expenseName, String category) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expense.setCategory(category);
                categories.add(category);
                return;
            }
        }
        throw new InvalidArgumentException(expenseName);
    }

    public static Expense getMaxExpense() throws InvalidArgumentException {
        if (expenses.isEmpty()) {
            throw new InvalidArgumentException("No expenses available");
        }
        Expense maxExpense = expenses.get(0);
        for (Expense expense : expenses) {
            if (expense.getAmount() > maxExpense.getAmount()) {
                maxExpense = expense;
            }
        }
        return maxExpense;
    }

    public static Expense getMinExpense() throws InvalidArgumentException {
        if (expenses.isEmpty()) {
            throw new InvalidArgumentException("No expenses available");
        }
        Expense minExpense = expenses.get(0);
        for (Expense expense : expenses) {
            if (expense.getAmount() < minExpense.getAmount()) {
                minExpense = expense;
            }
        }
        return minExpense;
    }

    public static List<Expense> getExpensesByDateRange(LocalDateTime start, LocalDateTime end) {
        ArrayList<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            LocalDateTime expenseDateTime = expense.getDateTime();
            if ((expenseDateTime.isEqual(start) || expenseDateTime.isAfter(start))
                    && (expenseDateTime.isEqual(end) || expenseDateTime.isBefore(end))) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    public static void setExpenses(List<Expense> loadedExpenses) {
        expenses.clear();
        expenses.addAll(loadedExpenses);
        totalExpense = 0;
        for (Expense expense : expenses) {
            totalExpense += expense.getAmount();
        }
    }

    public static void setCategories(Set<String> loadedCategories) {
        categories.clear();
        categories.addAll(loadedCategories);
    }
  
    public static List<Expense> getExpensesBySearchword(String searchword) {
        ArrayList<Expense> matchingExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getName().toLowerCase().contains(searchword.toLowerCase())) {
                matchingExpenses.add(expense);
            }
        }
        return matchingExpenses;
    }
}
