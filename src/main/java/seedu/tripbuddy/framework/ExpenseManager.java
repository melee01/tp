package seedu.tripbuddy.framework;

import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Has CRUD access to all user data.
 * */
public class ExpenseManager {

    private int budget;
    private final HashSet<String> categories = new HashSet<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();

    public ExpenseManager(int budget) {
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public List<String> getCategories() {
        return categories.stream().toList();
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Adds a new category name into {@code categories} if not exists.
     * */
    public void createCategory(String categoryName) {
        categories.add(categoryName);
    }

    /**
     * Adds a new {@link Expense} without category.
     * */
    public void addExpense(String name, int amount) {
        Expense expense = new Expense(name, amount);
        expenses.add(expense);
    }

    /**
     * Adds a new{@link Expense} with a specific category.
     * <ul>
     *     <li>A new category will be created if not exists.
     * </ul>
     * */
    public void addExpense(String name, int amount, String categoryName) {
        createCategory(categoryName);
        Expense expense = new Expense(name, amount);
        expenses.add(expense);
    }

    public Expense getExpense(int id) throws InvalidArgumentException {
        if (id < 0 || id >= expenses.size()) {
            throw new InvalidArgumentException(Integer.toString(id));
        }
        return expenses.get(id);
    }

    public void deleteExpense(int id) throws InvalidArgumentException {
        if (id < 0 || id >= expenses.size()) {
            throw new InvalidArgumentException(Integer.toString(id));
        }
        expenses.remove(id);
    }

    public List<Expense> getExpensesByCategory(String category) throws InvalidArgumentException {
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

    public void setExpenseCategory(String expenseName, String category) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expense.setCategory(category);
                return;
            }
        }
        throw new InvalidArgumentException(category);
    }

    public void deleteExpense(String expenseName) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expenses.remove(expense);
                return;
            }
        }
        throw new InvalidArgumentException(expenseName);
    }
}
