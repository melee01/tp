package seedu.tripbuddy.framework;

import seedu.tripbuddy.exception.InvalidArgumentException;


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
                        2. add-expense EXPENSE_NAME AMOUNT [CATEGORY] - Add a new expense.
                        3. delete-expense EXPENSE_NAME - Remove an expense by name.
                        4. create-category CATEGORY - Create a new expense category.
                        5. set-category EXPENSE_NAME CATEGORY - Assign an expense to a category.
                        6. view-budget - Check your remaining budget.
                        7. list-expense [CATEGORY] - Calculate sum of recorded expenses.
                        8. view-history - See a history of all expenses made.
                        
                        Enjoy tracking your expenses with TripBuddy!""";
    }

    public String handleViewBudget() {
        int budget = expenseManager.getBudget();
        return "Your current budget is $" + budget + ".";
    }

    public String handleSetBudget(int budget) {
        expenseManager.setBudget(budget);
        return "Your budget has been set to $" + budget + ".";
    }

    public String handleCreateCategory(String category) throws InvalidArgumentException {
        expenseManager.createCategory(category);
        return "Successfully created category: " + category + ".";
    }

    public String handleSetCategory(String expenseName, String category) throws InvalidArgumentException {
        expenseManager.setExpenseCategory(expenseName, category);
        return "Successfully set category for " + expenseName + "to" + category + ".";
    }

    public String handleDeleteExpense(String expenseName) throws InvalidArgumentException {
        expenseManager.deleteExpense(expenseName);
        return "Expense " + expenseName + " deleted successfully.\n" +
                "Your current budget is $" + expenseManager.getBudget() + ".";
    }

    public String handleAddExpense(String[] tokens) throws InvalidArgumentException {
        if (tokens.length >= 4) {
            expenseManager.addExpense(tokens[1], Integer.parseInt(tokens[2]), tokens[3]);
        } else {
            expenseManager.addExpense(tokens[1], Integer.parseInt(tokens[2]));
        }
        return "Expense " + tokens[1] + " added successfully.\n" +
                "Your current budget is $" + expenseManager.getBudget() + ".";

    }
}
