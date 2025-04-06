package seedu.tripbuddy.framework;

import org.json.JSONException;
import org.json.JSONObject;
import seedu.tripbuddy.command.Command;
import seedu.tripbuddy.dataclass.Currency;
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
    public static Currency baseCurrency = Currency.SGD;
    public static final int DEFAULT_BUDGET = 1000;
    private double budget;
    private double totalExpense = 0;
    private final HashSet<String> categories = new HashSet<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();
    private final HashSet<String> expenseNames = new HashSet<>();

    /**
     * Clears all existing data and initializes budget value. Uses {@code SGD} for default currency.
     */
    public ExpenseManager(double budget) {
        assert budget > 0 : "Budget must be positive";
        this.budget = budget;
        //this.baseCurrency = Currency.SGD;
        clearExpensesAndCategories();
    }

    public ExpenseManager() {
        this(DEFAULT_BUDGET);
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency newBaseCurrency) {
        baseCurrency = newBaseCurrency;
        Currency.setBaseCurrency(baseCurrency);
    }

    public double getBudget() {
        return budget;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
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


    public void clearExpensesAndCategories() {
        expenses.clear();
        categories.clear();
        expenseNames.clear();
        totalExpense = 0;
    }

    public void setBudget(double budget) {
        assert budget > 0 : "Budget must be positive";
        this.budget = budget;
    }

    /**
     * Adds a new category name into {@code categories} if not exists.
     */
    public void createCategory(String categoryName) throws InvalidArgumentException {
        if (categories.contains(categoryName)) {
            throw new InvalidArgumentException(categoryName, "Category name already exists.");
        }
        categories.add(categoryName);
    }

    /**
     * Adds a new {@link Expense} without category.
     */
    public void addExpense(String name, double amount) throws InvalidArgumentException {
        assert amount > 0 : "Amount must be positive";
        if (expenseNames.contains(name)) {
            throw new InvalidArgumentException(name, "Expense name already exists.");
        }
        Expense expense = new Expense(name, amount);
        expenses.add(expense);
        expenseNames.add(name);
        totalExpense += amount;
    }

    /**
     * Adds a new {@link Expense} with a specific category.
     * <ul>
     *     <li>A new category will be created if not exists.
     * </ul>
     */
    public void addExpense(String name, double amount, String categoryName) throws InvalidArgumentException {
        assert amount > 0 : "Amount must be positive";
        if (expenseNames.contains(name)) {
            throw new InvalidArgumentException(name, "Expense name already exists.");
        }
        if (!categories.contains(categoryName)) {
            createCategory(categoryName);
        }
        Expense expense = new Expense(name, amount, categoryName);
        expenses.add(expense);
        expenseNames.add(name);
        totalExpense += amount;
    }

    public void addExpense(JSONObject expObj) throws JSONException {
        Expense expense = Expense.fromJSON(expObj);
        String name = expense.getName();
        if (expenseNames.contains(name)) {
            throw new JSONException("Expense \"" + name + "\" already exists. Skipping");
        }

        double amount = expense.getAmount();
        if (amount <= 0) {
            throw new JSONException('"' + name + "\": Expense amount should be more than 0.");
        }
        if (amount > Command.MAX_INPUT_VAL) {
            throw new JSONException('"' + name + "\": Expense amount should be no more than " +
                    Command.MAX_INPUT_VAL);
        }

        expenses.add(expense);
        expenseNames.add(name);
        totalExpense += amount;
    }

    public Expense getExpense(int id) throws InvalidArgumentException {
        if (id < 0 || id >= expenses.size()) {
            throw new InvalidArgumentException(Integer.toString(id), "id index out of bound");
        }
        return expenses.get(id);
    }

    public void deleteExpense(String expenseName) throws InvalidArgumentException {
        for (Expense expense : expenses) {
            if (expense.getName().equalsIgnoreCase(expenseName)) {
                expenses.remove(expense);
                expenseNames.remove(expenseName);
                totalExpense -= expense.getAmount();
                return;
            }
        }
        throw new InvalidArgumentException(expenseName, "Expense name not found.");
    }

    public List<Expense> getExpensesByCategory(String category) throws InvalidArgumentException {
        if (!categories.contains(category)) {
            throw new InvalidArgumentException(category, "Category name not found.");
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
                categories.add(category);
                return;
            }
        }
        throw new InvalidArgumentException(expenseName, "Expense name not found.");
    }

    public Expense getMaxExpense() throws InvalidArgumentException {
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

    public Expense getMinExpense() throws InvalidArgumentException {
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

    public List<Expense> getExpensesByDateRange(LocalDateTime start, LocalDateTime end) {
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

    public void setCategories(Set<String> loadedCategories) {
        categories.clear();
        categories.addAll(loadedCategories);
    }
  
    public List<Expense> getExpensesBySearchword(String searchword) {
        ArrayList<Expense> matchingExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getName().toLowerCase().contains(searchword.toLowerCase())) {
                matchingExpenses.add(expense);
            }
        }
        return matchingExpenses;
    }
}
