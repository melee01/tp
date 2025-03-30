package seedu.tripbuddy.framework;

import seedu.tripbuddy.dataclass.Currency;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Handles commands and return message strings.
 * */
public class CommandHandler {

    private static final int DEFAULT_BUDGET = 1000;

    private final ExpenseManager expenseManager;

    public CommandHandler() {
        this.expenseManager = new ExpenseManager(DEFAULT_BUDGET);
    }

    public String handleTutorial() {
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
                        
                        Enjoy tracking your expenses with TripBuddy!""";
    }

    public String handleViewBudget() {
        double budget = expenseManager.getBudget();
        double totalExpense = expenseManager.getTotalExpense();
        double remainingBudget = budget - totalExpense;
        return "The original budget you set was $" + budget + ".\nSo far, you have spent $" +
                totalExpense + ".\nThis leaves you with a remaining budget of $" +
                String.format("%.2f", remainingBudget) + ".";
    }

    public String handleSetBudget(double budget) throws InvalidArgumentException {
        if (budget <= 0) {
            throw new InvalidArgumentException(Double.toString(budget));
        }
        expenseManager.setBudget(budget);
        return "Your budget has been set to $" + String.format("%.2f", budget) + ".";
    }

    public String handleAdjustBudget(double budget) throws InvalidArgumentException {
        if (budget <= 0) {
            throw new InvalidArgumentException(Double.toString(budget));
        }
        expenseManager.setBudget(budget);
        return "Your budget has been updated to $" + budget + ".\nYou have $" +
                String.format("%.2f", expenseManager.getRemainingBudget()) + " remaining to spend!";
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
        return "Expense " + expenseName + " deleted successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", expenseManager.getRemainingBudget()) + ".";
    }

    public String handleAddExpense(String expenseName, double amount, String category) throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }

        try {
            /* the value entered is a currency */
            Currency currency = Currency.valueOf(category);
            expenseManager.addExpense(expenseName, currency.convert(amount));
        } catch (IllegalArgumentException e) {
            /* the value entered is a category */
            expenseManager.addExpense(expenseName, amount, category);
        }

        return "Expense " + expenseName + " added successfully to category " + category + ".\n" +
                "Your remaining budget is $" + expenseManager.getRemainingBudget() + ".";
    }

    public String handleAddExpense(String expenseName, double amount, String category, String currencyStr) throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }
        try {
            Currency currency = Currency.valueOf(currencyStr);
            expenseManager.addExpense(expenseName, currency.convert(amount), category);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(currencyStr);
        }
        return "Expense " + expenseName + " added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", expenseManager.getRemainingBudget()) + ".";
    }

    public String handleAddExpense(String expenseName, double amount) throws InvalidArgumentException {
        if (amount <= 0) {
            throw new InvalidArgumentException(Double.toString(amount));
        }
        expenseManager.addExpense(expenseName, amount);
        return "Expense " + expenseName + " added successfully.\n" +
                "Your remaining budget is $" + String.format("%.2f", expenseManager.getRemainingBudget()) + ".";
    }

    public String handleListExpense(String category) throws InvalidArgumentException {
        List<Expense> expenses = (category == null? expenseManager.getExpenses() :
                expenseManager.getExpensesByCategory(category));
        StringBuilder expensesString = new StringBuilder();
        for (Expense expense : expenses) {
            expensesString.append("\n - ").append(expense.toString());
        }
        return expenses.isEmpty()? "There are no expenses." : "Expense list is: " + expensesString;
    }

    public String handleViewHistory() {
        StringBuilder expensesString = new StringBuilder("The history of all expenses made is: ");
        for (Expense expense : expenseManager.getExpenses()) {
            expensesString.append("\n - ").append(expense.toString());

        }
        return expensesString.toString();
    }
}
