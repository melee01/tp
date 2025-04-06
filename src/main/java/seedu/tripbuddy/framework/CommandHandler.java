package seedu.tripbuddy.framework;

import seedu.tripbuddy.dataclass.Currency;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;


/**
 * Handles commands and return message strings.
 */
public class CommandHandler {

    private static CommandHandler instance = null;

    private final ExpenseManager expenseManager;

    private CommandHandler() {
        this.expenseManager = ExpenseManager.getInstance();
    }

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    public String handleTutorial() {
        return """
                Welcome to the tutorial of TripBuddy!
                
                Format Guidelines:
                - Square brackets [] indicate optional elements.
                - AMOUNT must be a positive number.
                
                Here are the commands you can use:
                
                tutorial
                        - Displays a message explaining features.
                set-base-currency CURRENCY
                        - It sets the new currency.
                        - By default, the base currency is SGD.
                view-currency
                        - Displays the actual rates between currencies with the base currency.
                set-budget AMOUNT
                        - Set your total trip budget.
                        - Default budget is 1000 in base currency.
                view-budget
                        - Check your remaining budget.
                add-expense EXPENSE_NAME -a AMOUNT [-c CATEGORY]
                        - Add a new expense.
                delete-expense EXPENSE_NAME
                        - Remove an expense by name.
                list-expense [CATEGORY]
                        - Show all expenses, or expenses under a category if CATEGORY is given.
                        - Calculate sum of recorded expenses.
                search SEARCHWORD
                        - Displays expenses that include the given search word.
                max-expense
                        - Display an expense with the highest amount.
                min-expense
                        - Display an expense with the lowest amount.
                filter-date -f yyyy-MM-dd HH:mm:ss -t yyyy-MM-dd HH:mm:ss
                        - Get all expenses within date range, inclusive.
                create-category CATEGORY
                        - Create a new expense category.
                set-category EXPENSE_NAME -c CATEGORY
                        - Assign an expense to a category.
                view-categories
                        - Displays all categories.
                clear
                        - Clears all past expenses and categories.
                quit
                        - Exits the program.
                
                Enjoy tracking your expenses with TripBuddy!""";
    }

    public String handleViewBudget() {
        double budget = expenseManager.getBudget();
        double totalExpense = expenseManager.getTotalExpense();
        double remainingBudget = budget - totalExpense;
        Currency baseCurrency = expenseManager.getBaseCurrency();
        if (remainingBudget > 0) {
            return "The original budget you set was " + baseCurrency.getFormattedAmount(budget) +
                    ".\nSo far, you have spent " + baseCurrency.getFormattedAmount(totalExpense) +
                    ".\nThis leaves you with a remaining budget of " +
                    baseCurrency.getFormattedAmount(remainingBudget) + ".";
        }
        return "The original budget you set was " + baseCurrency.getFormattedAmount(budget) +
                ".\nSo far, you have spent " + baseCurrency.getFormattedAmount(totalExpense) +
                ".\nUh oh! You have exceeded your budget by " +
                baseCurrency.getFormattedAmount(-remainingBudget) +
                ".\nConsider adjusting your budget to get back on track.";
    }

    public String handleSetBudget(double budget) throws InvalidArgumentException {
        assert budget > 0;
        expenseManager.setBudget(budget);
        Currency baseCurrency = expenseManager.getBaseCurrency();
        return "Your budget has been set to " + baseCurrency.getFormattedAmount(budget) + ".";
    }

    public String handleCreateCategory(String category) throws InvalidArgumentException {
        expenseManager.createCategory(category);
        return "Successfully created category: " + category + ".";
    }

    public String handleSetCategory(String expenseName, String category) throws InvalidArgumentException {
        expenseManager.setExpenseCategory(expenseName, category);
        return "Successfully set category for " + expenseName + " to " + category + ".";
    }

    public String handleDeleteExpense(String expenseName) throws InvalidArgumentException {
        expenseManager.deleteExpense(expenseName);
        return "Expense " + expenseName + " deleted successfully.\nYour remaining budget is " +
                expenseManager.getBaseCurrency().getFormattedAmount(expenseManager.getRemainingBudget()) + ".";
    }

    public String handleAddExpense(String expenseName, double amount, String category)
            throws InvalidArgumentException {
        assert amount > 0;
        try {
            /* the value entered is a currency */
            Currency currency = Currency.valueOf(category);
            expenseManager.addExpense(expenseName, currency.convert(amount));
        } catch (IllegalArgumentException e) {
            /* the value entered is a category */
            expenseManager.addExpense(expenseName, amount, category);
        }

        double remainingBudget = expenseManager.getRemainingBudget();
        Currency baseCurrency = expenseManager.getBaseCurrency();
        if (remainingBudget >= 0) {
            return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                    "Your remaining budget is " + baseCurrency.getFormattedAmount(remainingBudget) + ".";
        } else {
            double amountOfDebt = remainingBudget * -1;
            return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                    "Uh oh! You've exceeded your budget.\n" +
                    "You are now in debt by " +
                    baseCurrency.getFormattedAmount(amountOfDebt) + ". Time to rein it in!\n" +
                    "Consider adjusting your budget to get back on track!";
        }
    }

    public String handleAddExpense(String expenseName, double amount) throws InvalidArgumentException {
        assert amount > 0;
        expenseManager.addExpense(expenseName, amount);
        double remainingBudget = expenseManager.getRemainingBudget();
        Currency baseCurrency = expenseManager.getBaseCurrency();
        if (remainingBudget >= 0) {
            return "Expense " + expenseName + " added successfully.\n" +
                    "Your remaining budget is $" +
                    baseCurrency.getFormattedAmount(remainingBudget) + ".";
        }
        return "Expense " + expenseName + " added successfully.\n" +
                "Uh oh! You've exceeded your budget.\n" +
                "You are now in debt by $" + baseCurrency.getFormattedAmount(-remainingBudget) +
                ". Time to rein it in!\nConsider adjusting your budget to get back on track!";
    }

    public String handleListExpense(String category) throws InvalidArgumentException {
        List<Expense> expenses = (category == null? expenseManager.getExpenses() :
                expenseManager.getExpensesByCategory(category));
        StringBuilder expensesString = new StringBuilder();
        double totalAmount = 0;
        for (Expense expense : expenses) {
            expensesString.append("\n - ").append(expense.toString());
            totalAmount += expense.getAmount();
        }
        expensesString.append("\nTotal amount spent: ")
                .append(expenseManager.getBaseCurrency().getFormattedAmount(totalAmount)).append(".");
        return expenses.isEmpty() ? "There are no expenses." : "Here is a list of your past expenses: "
                + expensesString;
    }

    public String handleMaxExpense() throws InvalidArgumentException {
        Expense maxExpense = expenseManager.getMaxExpense();
        return "Maximum expense: " + maxExpense.toString();
    }

    public String handleMinExpense() throws InvalidArgumentException {
        Expense minExpense = expenseManager.getMinExpense();
        return "Minimum expense: " + minExpense.toString();
    }

    public String handleFilterExpenseByDateRange(String startStr, String endStr)
            throws DateTimeParseException, InvalidArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(startStr, formatter);
        LocalDateTime end = LocalDateTime.parse(endStr, formatter);

        List<Expense> filteredExpenses = expenseManager.getExpensesByDateRange(start, end);
        if (filteredExpenses.isEmpty()) {
            return "No expenses found between " + startStr + " and " + endStr + ".";
        } else {
            StringBuilder sb = new StringBuilder("Expenses between " + startStr + " and " + endStr + ":");
            for (Expense expense : filteredExpenses) {
                sb.append("\n - ").append(expense.toString());
            }
            return sb.toString();
        }
    }

    public String handleViewCurrency() {
        StringBuilder message = new StringBuilder("The current exchange rate against base currency is: \n");
        Currency baseCurrency = expenseManager.getBaseCurrency();
        for (Currency currency : Currency.values()) {
            if (currency != baseCurrency) {
                message.append("Rate of ")
                        .append(baseCurrency.toString())
                        .append(" and ")
                        .append(currency.toString())
                        .append(" is ")
                        .append(String.format("%.2f", currency.getRate()))
                        .append("\n");
            }
        }
        return message.toString();
    }

    public String handleSearch(String searchWord) {
        List<Expense> expenses = expenseManager.getExpensesBySearchword(searchWord);
        if (expenses.isEmpty()) {
            return "There are no expenses that matched your search word: " + searchWord + ".";
        } else {
            StringBuilder sb = new StringBuilder("Expenses that matched your search word '" + searchWord + "':");
            for (Expense expense : expenses) {
                sb.append("\n - ").append(expense.toString());
            }
            return sb.toString();
        }
    }

    public String handleViewCategories() {
        List<String> categories = expenseManager.getCategories();
        if (categories.isEmpty()) {
            return "There are no categories.";
        } else {
            StringBuilder sb = new StringBuilder("Here is the category list:");
            for (String category : categories) {
                sb.append("\n - ").append(category);
            }
            return sb.toString();
        }
    }

    public String handleClearAll() {
        expenseManager.clearExpensesAndCategories();
        return "All past expenses and categories have been cleared.";
    }

    public String handleSetBaseCurrency(String baseCurrency) throws InvalidArgumentException {
        Currency newBase;
        try {
            newBase = Currency.valueOf(baseCurrency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(baseCurrency, "Base currency is not a valid currency.");
        }

        // change budget
        double currentBudget = expenseManager.getBudget();
        expenseManager.setBudget(newBase.convert(currentBudget));

        // change total spent
        double currentTotalSpent = expenseManager.getTotalExpense();
        expenseManager.setTotalExpense(newBase.convert(currentTotalSpent));

        // change all expenses
        for (Expense expense : expenseManager.getExpenses()) {
            expense.setAmount(newBase.convert(expense.getAmount()));
        }

        expenseManager.setBaseCurrency(newBase);

        return "Current base is: " + newBase;
    }
}
