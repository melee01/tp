package seedu.tripbuddy.command;

import seedu.tripbuddy.framework.CommandHandler;

public enum Keyword {

    TUTORIAL,
    SET_BUDGET,
    ADJUST_BUDGET,
    VIEW_BUDGET,
    CREATE_CATEGORY,
    SET_CATEGORY,
    ADD_EXPENSE,
    DELETE_EXPENSE,
    LIST_EXPENSE,
    VIEW_HISTORY,
    MAX_EXPENSE,
    MIN_EXPENSE,
    FILTER_DATE,
    VIEW_CURRENCY;

    @Override
    public String toString() {
        return switch (this) {
            case TUTORIAL ->  "tutorial";
            case SET_BUDGET ->  "set-budget";
            case ADJUST_BUDGET ->  "adjust-budget";
            case VIEW_BUDGET ->  "view-budget";
            case CREATE_CATEGORY ->  "create-category";
            case SET_CATEGORY ->  "set-category";
            case ADD_EXPENSE ->  "add-expense";
            case DELETE_EXPENSE ->  "delete-expense";
            case LIST_EXPENSE ->  "list-expense";
            case VIEW_HISTORY ->  "view-history";
            case MAX_EXPENSE -> "max-expense";
            case MIN_EXPENSE -> "min-expense";
            case FILTER_DATE -> "filter-date";
            case VIEW_CURRENCY -> "view-currency";
        };
    }
}
