# User Guide

## Introduction

TripBuddy is a simple expense-tracking application designed to help users manage costs for a single trip. It allows 
travelers to log expenses, categorize them, and view a summary of their spendings in one place. TripBuddy ensures 
budgeting is easy and hassle-free, so travelers can focus on enjoying their trip.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `TripBuddy` from [here](http://link.to/duke).

## Notes about the command format

- Words in UPPER_CASE are the parameters to be supplied by the user.
e.g. in `set-budget AMOUNT`, AMOUNT is a parameter which can be used as `set-budget 1000`.
- Items in square brackets are optional.
- Parameters must be in the specified order.
- Extraneous parameters for commands that do not take in parameters (such as `view-budget` and `quit`) will be ignored.
e.g. if the command specifies quit 123, it will be interpreted as quit.

## Features 

### View tutorial : `tutorial`

Displays a message explaining key features.

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

- NAME must not contain spaces. If it consists of multiple words, use dashes or underscores instead.

Examples of usage:
- `create-category Accommodation`
- `create-category food-and-drink`


### Add Expense: `add-expense`

Adds an expense to the trip and automatically updates the remaining budget, taking into account 
the new expenditure.

Format: `add-expense NAME_EXPENSE AMOUNT [CATEGORY]`

- NAME_EXPENSE must not contain spaces. If it consists of multiple words, use dashes or underscores instead.

Examples of usage:
- `add-expense mcdonalds 5`
- `add-expense the-plaza-hotel 300 Accommodation`


### Set Category: `set-cateogry`

Set the category for a particular expense that has been already inputted by the user. This command will override a 
prior category that was set for that specific expense.

Format: `set-category NAME_EXPENSE CATEGORY`

- If CATEGORY does not exist in the existing record of categories, then a new category will be created with
the specified name.

Examples:
- `set-category mcdonalds food`

### Delete Expense: `delete-expense`

Removes an expense from the trip.

Format: `delete-expense NAME_EXPENSE`

Examples of usage:
- delete-expense the-plaza-hotel

### Exit the program: `quit`

Exits the program.

Format: `quit`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
