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

    public static String handleTutorial() {
        return """
                Welcome to the tutorial of TripBuddy!
                
                Format Guidelines:
                - Square brackets [] indicate optional elements.
                - Expense and category names must be a single word or multiple words joined with a dash (-).
                - AMOUNT must be a positive integer.
                
                Here are the commands you can use:
                1. set-budget AMOUNT
                        - Set your total trip budget. Default budget is $1000.
                2. add-expense EXPENSE_NAME -a AMOUNT -c [CATEGORY]
                        - Add a new expense.
                3. delete-expense EXPENSE_NAME
                        - Remove an expense by name.
                4. create-category CATEGORY
                        - Create a new expense category.
                5. set-category EXPENSE_NAME -c CATEGORY
                        - Assign an expense to a category.
                6. view-budget
                        - Check your remaining budget.
                7. list-expense [CATEGORY]
                        - Calculate sum of recorded expenses.
                8. view-history
                        - See a history of all expenses made.
                9. adjust-budget AMOUNT
                        - Modify the budget amount.
                10. max-expense
                        - Display the expense with the highest amount.
                11. min-expense
                        - Display the expense with the lowest amount.
                12. filter-date -f yyyy-MM-dd HH:mm:ss -t yyyy-MM-dd HH:mm:ss
                        - Get all expenses within date range.
                - Filter expenses between two date/time ranges START_DATE START_TIME END_DATE END_TIME.
                13. view-currency
                        - Displays the actual rates of currencies.
                        - default base currency: SGD
                14. search SEARCHWORD
                        - Displays expenses that include the given search word.
                15. view-categories
                        - Displays all categories.
                16. clear
                        - Clears all past expenses and categories.
                17. set-base-currency CURRENCY
                        - It sets the new currency.
                        - By default, the base currency is SGD.
                
                Enjoy tracking your expenses with TripBuddy!""";
    }

    public static String handleViewBudget() {
        double budget = ExpenseManager.getBudget();
        double totalExpense = ExpenseManager.getTotalExpense();
        double remainingBudget = budget - totalExpense;
        if (remainingBudget > 0) {
            return "The original budget you set was " + ExpenseManager.getFormattedAmount(budget) +
                    ".\nSo far, you have spent " + ExpenseManager.getFormattedAmount(totalExpense) +
                    ".\nThis leaves you with a remaining budget of " +
                    ExpenseManager.getFormattedAmount(remainingBudget) + ".";
        }
        return "The original budget you set was " + ExpenseManager.getFormattedAmount(budget) +
                ".\nSo far, you have spent " + ExpenseManager.getFormattedAmount(totalExpense) +
                ".\nUh oh! You have exceeded your budget by " +
                ExpenseManager.getFormattedAmount(-remainingBudget) +
                ".\nConsider adjusting your budget to get back on track.";
    }

    public static String handleSetBudget(double budget) throws InvalidArgumentException {
        assert budget > 0;
        ExpenseManager.setBudget(budget);
        return "Your budget has been set to " + ExpenseManager.getFormattedAmount(budget) + ".";
    }


    public static String handleAdjustBudget(double budget) throws InvalidArgumentException {
        assert budget > 0;
        ExpenseManager.setBudget(budget);
        return "Your budget has been updated to $" + budget + ".\nYou have $" +
                String.format("%.2f", ExpenseManager.getRemainingBudget()) + " remaining to spend!";
    }

    public static String handleCreateCategory(String category) throws InvalidArgumentException {
        ExpenseManager.createCategory(category);
        return "Successfully created category: " + category + ".";
    }

    public static String handleSetCategory(String expenseName, String category) throws InvalidArgumentException {
        ExpenseManager.setExpenseCategory(expenseName, category);
        return "Successfully set category for " + expenseName + " to " + category + ".";
    }

    public static String handleDeleteExpense(String expenseName) throws InvalidArgumentException {
        ExpenseManager.deleteExpense(expenseName);
        return "Expense " + expenseName + " deleted successfully.\nYour remaining budget is " +
                ExpenseManager.getFormattedAmount(ExpenseManager.getRemainingBudget()) + ".";
    }

    public static String handleAddExpense(String expenseName, double amount, String category)
            throws InvalidArgumentException {
        assert amount > 0;
        try {
            /* the value entered is a currency */
            Currency currency = Currency.valueOf(category);
            ExpenseManager.addExpense(expenseName, currency.convert(amount));
        } catch (IllegalArgumentException e) {
            /* the value entered is a category */
            ExpenseManager.addExpense(expenseName, amount, category);
        }

        double remainingBudget = ExpenseManager.getRemainingBudget();
        if (remainingBudget >= 0) {
            return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                    "Your remaining budget is " + ExpenseManager.getFormattedAmount(remainingBudget) + ".";
        } else {
            double amountOfDebt = remainingBudget * -1;
            return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                    "Uh oh! You've exceeded your budget.\n" +
                    "You are now in debt by " +
                    ExpenseManager.getFormattedAmount(amountOfDebt) + ". Time to rein it in!\n" +
                    "Consider adjusting your budget to get back on track!";
        }
    }

    public static String handleAddExpense(String expenseName, double amount, String category, String currencyStr)
            throws InvalidArgumentException {
        assert amount > 0;
        try {
            Currency currency = Currency.valueOf(currencyStr);
            ExpenseManager.addExpense(expenseName, currency.convert(amount), category);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(currencyStr, "Invalid currency.");
        }
        double remainingBudget = ExpenseManager.getRemainingBudget();
        if (remainingBudget >= 0) {
            return "Expense " + expenseName + " added successfully.\n" +
                    "Your remaining budget is $" + String.format("%.2f", remainingBudget) + ".";
        } else {
            double amountOfDebt = remainingBudget * -1;
            return "Expense " + expenseName + " added successfully.\n" +
                    "Uh oh! You've exceeded your budget.\n" +
                    "You are now in debt by $" + amountOfDebt + ". Time to rein it in!\n" +
                    "Consider adjusting your budget to get back on track!";
        }
    }

    public static String handleAddExpense(String expenseName, double amount) throws InvalidArgumentException {
        assert amount > 0;
        ExpenseManager.addExpense(expenseName, amount);
        double remainingBudget = ExpenseManager.getRemainingBudget();
        if (remainingBudget >= 0) {
            return "Expense " + expenseName + " added successfully.\n" +
                    "Your remaining budget is $" + ExpenseManager.getFormattedAmount(remainingBudget) + ".";
        }
        return "Expense " + expenseName + " added successfully.\n" +
                "Uh oh! You've exceeded your budget.\n" +
                "You are now in debt by $" + ExpenseManager.getFormattedAmount(-remainingBudget) +
                ". Time to rein it in!\nConsider adjusting your budget to get back on track!";
    }

    public static String handleListExpense(String category) throws InvalidArgumentException {
        List<Expense> expenses = (category == null? ExpenseManager.getExpenses() :
                ExpenseManager.getExpensesByCategory(category));
        StringBuilder expensesString = new StringBuilder();
        double totalAmount = 0;
        for (Expense expense : expenses) {
            expensesString.append("\n - ").append(expense.toString());
            totalAmount += expense.getAmount();
        }
        expensesString.append("\nTotal amount spent: ")
                .append(ExpenseManager.getFormattedAmount(totalAmount)).append(".");
        return expenses.isEmpty() ? "There are no expenses." : "Here is a list of your past expenses: "
                + expensesString;
    }

    public static String handleViewHistory() {
        StringBuilder expensesString = new StringBuilder("Here is a history of your past expenses: ");
        for (Expense expense : ExpenseManager.getExpenses()) {
            expensesString.append("\n - ").append(expense.toString());

        }
        return expensesString.toString();
    }

    public static String handleMaxExpense() throws InvalidArgumentException {
        Expense maxExpense = ExpenseManager.getMaxExpense();
        return "Maximum expense: " + maxExpense.toString();
    }

    public static String handleMinExpense() throws InvalidArgumentException {
        Expense minExpense = ExpenseManager.getMinExpense();
        return "Minimum expense: " + minExpense.toString();
    }

    public static String handleFilterExpenseByDateRange(String startStr, String endStr)
            throws DateTimeParseException, InvalidArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(startStr, formatter);
        LocalDateTime end = LocalDateTime.parse(endStr, formatter);

        List<Expense> filteredExpenses = ExpenseManager.getExpensesByDateRange(start, end);
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

    public static String handleViewCurrency() {
        StringBuilder message = new StringBuilder("The current exchange rate against base currency is: \n");
        for (Currency currency : Currency.values()) {
            message.append("Rate of base currency and ")
                    .append(currency.toString())
                    .append(" is ")
                    .append(String.format("%.2f", currency.getRate()))
                    .append("\n");
        }
        return message.toString();
    }

    public static String handleSearch(String searchWord) {
        List<Expense> expenses = ExpenseManager.getExpensesBySearchword(searchWord);
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

    public static String handleViewCategories() {
        List<String> categories = ExpenseManager.getCategories();
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

    public static String handleClearAll() {
        ExpenseManager.clearExpensesAndCategories();
        return "All past expenses and categories have been cleared.";
    }

    public static String handleSetBaseCurrency(String baseCurrency) throws InvalidArgumentException {
        Currency newBase;
        try {
            newBase = Currency.valueOf(baseCurrency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(baseCurrency, "Base currency is not a valid currency.");
        }

        // change budget
        double currentBudget = ExpenseManager.getBudget();
        ExpenseManager.setBudget(newBase.convert(currentBudget));

        // change total spent
        double currentTotalSpent = ExpenseManager.getTotalExpense();
        ExpenseManager.setTotalExpense(newBase.convert(currentTotalSpent));

        // change all expenses
        for (Expense expense : ExpenseManager.getExpenses()) {
            expense.setAmount(newBase.convert(expense.getAmount()));
        }

        ExpenseManager.setBaseCurrency(newBase);

        return "Current base is: " + newBase;
    }
}
