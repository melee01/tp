# Nora Daza Pérez - Project Portfolio Page

## Overview
TripBuddy is a simple expense-tracking application designed to help users manage costs for a single trip. It allows
travelers to log expenses, categorize them, and view a summary of their spendings in one place.

### Summary of Contributions
#### **Code Contributed**
This is my [Contribution Page](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=noradazaperez&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

#### Enhancements Implemented
I implemented the following commands: 
* The multi-currency support:
  * `add-expense AMOUNT CATEGORY [CURRENCY]` : a command that adds an expense with a given currency 
  * `view-currency` : a command that displays the exchange rate between the base currency and the available currencies
  * `set-base-currency` : a command that changes the base currency 
  * `Currency` class that represents the different currencies 
* `view-history` : a command that displays the past expenses

Contributions to the UG: Which sections did you contribute to the UG?
#### Contributions to the DG: 
##### **Multi-currency support** 
I explained what the different features the multi-currency support has:
* How the conversion logic works
* How does `view-currency` work using a sequence diagram 

##### **Design**
I explained the different aspects of the design, adding class diagrams for that, including:
* **CommandHandler**  : what the main functions of the `CommandHandler` are 
* **ExpenseManager**  : how the `ExpenseManager` class and the `Expense` class interact with each other
* **InputHandler**    : how the `Parser` and the `Command` classes work 
* **Ui**              : what the main functions of the `Ui` are 

### Contributions to team-based tasks
The tasks I did included:
* Releasing v2.0 and closing the milestone 
* Adding issues to the issue tracker 
* Documenting the target user profile 

### Mentoring contributions
* Brainstorming for new enhancements for v2.0 
* Updating the new features list 

