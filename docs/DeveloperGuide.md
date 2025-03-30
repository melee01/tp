# Developer Guide

## Acknowledgements

- [JSON-java](https://github.com/stleary/JSON-java): a third-party library for
JSON conversion and parsing.

## Design

This section describes some details on the design.

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

## Implementation
This section describes an explanation on some of the implemented features. 

### Multi-currency support 
Allows users to have multi-currency expenses in the app.
#### **Step 1**: Conversion rates storage
We use a simple dictionary that maps currency codes to their conversion rates relative to the base
currency.

* For example:

```json
{
    "SGD": 1.0,
    "EUR": 1.1,
    "JPY": 0.009,
    "GBP": 1.3
}
``` 

We can also see the current currency rates available in our app using the command 
`view-rate`. This will display the above `json`. 

Moreover, we can also change the base currency. In order to do this, we will:
1. Recalculate existing expenses: all previously recorded expenses need to be recalculated to reflect their values in the neew base currency. 
2. Update the budget: update the value of the budget so that is consistent with the current currency.
3. Recalculate Conversion Rates: divide all the values in the conversion dictionary by the current rate to that currency. 


#### **Step 2**: Conversion logic
When an expense is added:
1. If a different currency is given, a conversion is needed
2. Multiply the expense amount by the conversion rate
3. The value in the base 

#### [Optional] **Step 3**: Updating conversion rates
A command that manually updates/adds conversion rates.
`update-rate CURRENCY_CODE NEW_RATE`

#### **Alternatives Considered**
- Keep the original value of the expense as well as the converted value. 

## Product scope
### Target user profile

**Target user profile**
- Individuals who are planning a trip or currently traveling.
- Budget-travelers who intend to establish a budget and track their expenses effectively.
- Users who prefer to use keyboard-based interactions over mouse-based navigation.
- Users who are comfortable using command-line interface (CLI) applications.

### Value proposition

TripBuddy helps travelers stay on top of their finances by simplifying expense tracking 
and budgeting during trips. TripBuddy provides an intuitive platform to log expenses and 
track spending in real time. By eliminating the confusion of manual calculations, TripBuddy
ensures a stress-free travel experience where everyone stays financially organized and accountable.

## User Stories

| Version | As a ...        | I want to ...            | So that I can ...                                      |
|---------|-----------------|--------------------------|--------------------------------------------------------|
| v1.0    | new user        | see usage instructions   | refer to them when I forget how to use the app         |
| v1.0    | budget-traveler | set a travel budget      | monitor spending to avoid overspending during the trip |
| v1.0    | user            | add an expense           | track my expenses and total remaining budget.          |
| v1.0    | user            | delete an expense        | correct an earlier mistake.                            |
| v1.0    | user            | view my remaining budget | see how much money I have left to spend.               |
| v1.0    | user            | adjust my total budget   | modify my spending habits accordingly.                 |
| v1.0    | user            | view past expenses       | review my past expenses for accuracy                   |

## Non-Functional Requirements

* Performance: The application should process user commands within 1 second.
* Usability: The system should have an intuitive CLI interface with clear error messages and user guidance.
* Reliability: TripBuddy should be able to handle at least 100 expense entries without crashing.
* Portability: The application should be compatible with Windows, macOS, and Linux environments.
* Scalability: The system should allow future enhancements, such as more expense categories, without significant rework.

## Glossary

* *expense* - a record of a purchase made by the user, stored as an `Expense`
entity.
* *budget* – a spending limit set by the user to track expenses and manage finances during a trip.
* *category* – a classification assigned to an expense (e.g., Food, Transport) to help users organize their spending.

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
