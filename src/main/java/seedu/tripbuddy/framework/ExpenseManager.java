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

    private double budget;
    private double totalExpense = 0;
    private final HashSet<String> categories = new HashSet<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();

    public ExpenseManager(double budget) {
        assert budget > 0 : "Budget must be positive";
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getRemainingBudget() {
        return budget - totalExpense;
    }

    public List<String> getCategories() {
        return categories.stream().toList();
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public void setBudget(double budget) {
        assert budget > 0 : "Budget must be positive";
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
    public void addExpense(String name, double amount) {
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
     * */
    public void addExpense(String name, double amount, String categoryName) {
        assert amount > 0 : "Amount must be positive";
        createCategory(categoryName);
        Expense expense = new Expense(name, amount, categoryName);
        expenses.add(expense);
        totalExpense += amount;
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
        totalExpense -= expenses.get(id).getAmount();
        expenses.remove(id);
    }

    public void deleteExpense(String expenseName) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expenses.remove(expense);
                totalExpense -= expense.getAmount();
                return;
            }
        }
        throw new InvalidArgumentException(expenseName);
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
        throw new InvalidArgumentException(expenseName);
    }
}
