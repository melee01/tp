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
                        1. set-budget AMOUNT - Set your total trip budget. Default budget is $1000.
                        2. add-expense EXPENSE_NAME AMOUNT [CATEGORY] [CURRENCY] - Add a new expense.
                        3. delete-expense EXPENSE_NAME - Remove an expense by name.
                        4. create-category CATEGORY - Create a new expense category.
                        5. set-category EXPENSE_NAME CATEGORY - Assign an expense to a category.
                        6. view-budget - Check your remaining budget.
                        7. list-expense [CATEGORY] - Calculate sum of recorded expenses.
                        8. view-history - See a history of all expenses made.
                        9. adjust-budget AMOUNT - Modify the budget amount.
                        10. max-expense - Display the expense with the highest amount.
                        11. min-expense - Display the expense with the lowest amount.
                        12. filter-date yyyy-MM-dd HH:mm:ss yyyy-MM-dd HH:mm:ss
                        - Filter expenses between two date/time ranges START_DATE START_TIME END_DATE END_TIME.
                        13. view-currency - Displays the actual rates of currencies.
                        
                        Enjoy tracking your expenses with TripBuddy!""";
    }

    public static String handleViewBudget() {
        double budget = ExpenseManager.getBudget();
        double totalExpense = ExpenseManager.getTotalExpense();
        double remainingBudget = budget - totalExpense;
        return "The original budget you set was $" + budget + ".\nSo far, you have spent $" +
                totalExpense + ".\nThis leaves you with a remaining budget of $" +
                String.format("%.2f", remainingBudget) + ".";
    }

    public static String handleSetBudget(double budget) throws InvalidArgumentException {
        if (budget <= 0) {
            throw new InvalidArgumentException(Double.toString(budget));
        }
        ExpenseManager.setBudget(budget);
        return "Your budget has been set to $" + String.format("%.2f", budget) + ".";
    }

    public static String handleAdjustBudget(double budget) throws InvalidArgumentException {
        if (budget <= 0) {
            throw new InvalidArgumentException(Double.toString(budget));
        }
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
        return "Expense " + expenseName + " deleted successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", ExpenseManager.getRemainingBudget()) + ".";
    }

    public static String handleAddExpense(String expenseName, double amount, String category)
            throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }

        try {
            /* the value entered is a currency */
            Currency currency = Currency.valueOf(category);
            ExpenseManager.addExpense(expenseName, currency.convert(amount));
        } catch (IllegalArgumentException e) {
            /* the value entered is a category */
            ExpenseManager.addExpense(expenseName, amount, category);
        }

        return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                "Your remaining budget is $" + ExpenseManager.getRemainingBudget() + ".";
    }

    public static String handleAddExpense(String expenseName, double amount, String category, String currencyStr)
            throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }
        try {
            Currency currency = Currency.valueOf(currencyStr);
            ExpenseManager.addExpense(expenseName, currency.convert(amount), category);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(currencyStr);
        }
        return "Expense " + expenseName + " added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", ExpenseManager.getRemainingBudget()) + ".";
    }

    public static String handleAddExpense(String expenseName, double amount) throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }
        ExpenseManager.addExpense(expenseName, amount);
        return "Expense " + expenseName + " added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", ExpenseManager.getRemainingBudget()) + ".";
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
        return expenses.isEmpty()? "There are no expenses." : "Expense list (date and time included): "
                + expensesString;
    }

    public static String handleViewHistory() {
        StringBuilder expensesString = new StringBuilder("The history of all expenses made is: ");
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

    public static String handlerViewCurrency() {
        StringBuilder message = new StringBuilder("The current exchange rate is: \n");
        for (Currency currency : Currency.values()) {
            message.append("Rate of base currency and ")
                    .append(currency.toString())
                    .append(" is ")
                    .append(currency.getRate())
                    .append(" \n");
        }

        return message.toString();
    }
}
