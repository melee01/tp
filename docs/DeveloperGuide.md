# Developer Guide

## Acknowledgements

- [JSON-java](https://github.com/stleary/JSON-java): a third-party library for
JSON conversion and parsing.

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

TripBuddy allows user interactions via a CLI, which is activated by `TripBuddy`.

The main framework consists of three layers: `command.Parser`, `framework.CommandHandler`
and `framework.ExpenseManager`, both following the singleton design pattern.
This layered design results in less conflicts while developing multiple
features, as well as ensuring testability of different modules.

### ExpenseManager

`ExpenseManager` stores all user data, and has direct CRUD access to them.

Return values of methods of `ExpenseManager` are unprocessed, i.e. not parsed
into `String` or other formats for UI output.

E.g., the following method from `ExpenseManager`returns a list of `Expense`
entities. 

``` java
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
```

### CommandHandler

`CommandHandler` is responsible for collecting data from `ExpenseManager` and
processing then into `String` messages to be shown on UI.

Parameters of `CommandHandler` methods should be parsed by `Parser` already.

E.g., the following method from `CommandHandler` receives an `int` as a
parameter and returns a `String` message for display.

``` java
public String handleSetBudget(int budget) throws InvalidArgumentException {
    if (budget <= 0) {
        throw new InvalidArgumentException(Integer.toString(budget));
    }
    expenseManager.setBudget(budget);
    return "Your budget has been set to $" + budget + ".";
}
```

### Parser

`Parser` is responsible for the following tasks:

- Analyzing user input and converting arguments into correct data types for
`CommandHandler` methods.

- Invoking `CommandHandler` methods for command execution.

- Handling exceptions caused by user actions.

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *expense* - a record of a purchase made by the user, stored as an `Expense`
entity.

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
