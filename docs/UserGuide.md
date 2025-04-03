# User Guide

## Introduction

TripBuddy is a simple expense-tracking application designed to help users manage costs for a single trip. It allows 
travelers to log expenses, categorize them, and view a summary of their spendings in one place. TripBuddy ensures 
budgeting is easy and hassle-free, so travelers can focus on enjoying their trip.

## Notes about the command format

- Words in UPPER_CASE are the parameters to be supplied by the user.
e.g. in `set-budget AMOUNT`, AMOUNT is a parameter which can be used as `set-budget 1000`.
- Items in square brackets are optional.
- Parameters must be in the specified order.
- Extraneous parameters for commands that do not take in parameters (such as `view-budget` and `quit`) will be ignored.
e.g. if the command specifies quit 123, it will be interpreted as quit.

## Features 

### View tutorial : `tutorial`

Displays a message explaining full features.

Format: `tutorial`

### Setting budget : `set-budget`

Sets a total budget for this trip.

Format: `set-budget BUDGET_NUMBER`

Examples of usage:
- `set-budget 750`
- `set-budget 1000`

### Adjust budget : `adjust-budget`

Modifies the initial budget set.

Format: `adjust-budget 1200`

- Previous expenses are not overridden, and remaining budget is still calculated using prior expenses.

Examples of usage:
- `adjust-budget 750`

### Create Category: `create-category`

Creates a category for storing expenses, such as accommodation or food.

Format:  `create-category NAME`

Examples of usage:
- `create-category Accommodation`
- `create-category food and drink`


### Add Expense: `add-expense`

Adds an expense to the trip and automatically updates the remaining budget, taking into account 
the new expenditure.

Format: `add-expense NAME_EXPENSE -a AMOUNT [CURRENCY] -c [CATEGORY]`

- Base currency is SGD. Everything will be converted to the base currency. 

Examples of usage:
- `add-expense mcdonalds -a 5`
- `add-expense the plaza hotel -a 300 -c Accommodation`
- `add-expense lunch -a 100 USD`
- `add-expense capybara museum -a 10000 IDR -c Activities `

### Set Category: `set-cateogry`

Set the category for a particular expense that has been already inputted by the user. This command will override a 
prior category that was set for that specific expense.

Format: `set-category NAME_EXPENSE -c CATEGORY`

- If CATEGORY does not exist in the existing record of categories, then a new category will be created with
the specified name.

Examples:
- `set-category mcdonalds -c food`

### Delete Expense: `delete-expense`

Removes an expense from the trip.

Format: `delete-expense NAME_EXPENSE`

Examples of usage:
- delete-expense the-plaza-hotel

### Search Expense: `search`

Search the expense list by search word.

Format: `search SEARCHWORD`

Examples of usage:
- search shopping

### Max/min Expense: `max-expense`/`min-expense`

Display the expense with the highest/lowest amount.

Format: `max-expense`
 
### Filter Date: `filter-date`

Get all expenses within date range.

Format: `filter-date -f yyyy-MM-dd HH:mm:ss -t yyyy-MM-dd HH:mm:ss`

### View Currency: `view-currency`
    
Displays the actual rates of currencies.
- default base currency: SGD

Format: `view-currency`

### View Categories: `view-categories`

Displays all categories.

Format: `view-categories`

### Clear: `clear`
    
Clears all past expenses and categories.

Format: `clear`

### Set Base Currency: `set-base-currency`

Sets the new currency.
- By default, the base currency is SGD.

Format: `set-base-currency CURRENCY`

### Exit the program: `quit`

Exits the program.

Format: `quit`
