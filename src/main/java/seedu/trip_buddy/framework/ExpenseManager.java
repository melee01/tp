package seedu.trip_buddy.framework;

import seedu.trip_buddy.dataclass.Expense;

import java.util.*;

/**
 * Stores all user data.
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
     * <ul><li>A new category will be created if not exists.
     * */
    public void addExpense(String name, int amount, String categoryName) {
        createCategory(categoryName);
        Expense expense = new Expense(name, amount);
        expenses.add(expense);
    }
}
