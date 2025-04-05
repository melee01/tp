package seedu.tripbuddy.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.tripbuddy.command.Command;
import seedu.tripbuddy.dataclass.Currency;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.framework.ExpenseManager;
import seedu.tripbuddy.framework.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataHandler {

    private static final Logger LOGGER = Logger.getLogger("TripBuddy");

    public static double round2Digits(double x) {
        assert x > 0 && x <= Command.MAX_INPUT_VAL;
        return (int)(x * 100 + .5) / 100.;
    }

    public static void saveData(String path) throws IOException {
        JSONObject root = new JSONObject();
        root.put("currency", ExpenseManager.getBaseCurrency().toString());
        root.put("budget", round2Digits(ExpenseManager.getBudget()));

        LOGGER.log(Level.INFO, "budget converted");

        JSONArray categoriesArr = new JSONArray();
        for (String category : ExpenseManager.getCategories()) {
            categoriesArr.put(category);
        }
        root.put("categories", categoriesArr);

        LOGGER.log(Level.INFO, "categories converted");

        JSONArray expensesArr = new JSONArray();
        for (Expense expense : ExpenseManager.getExpenses()) {
            JSONObject expObj = expense.toJSON();
            expensesArr.put(expObj);
            LOGGER.log(Level.INFO, "expense converted: " + expObj);
        }
        root.put("expenses", expensesArr);

        LOGGER.log(Level.INFO, "expenses converted");

        String absPath = FileHandler.writeJsonObject(path, root);
        Ui.printMessage("Saved data to file:\n\t" + absPath);
    }

    public static void loadData(String path) throws FileNotFoundException, JSONException {
        JSONObject root = FileHandler.readJsonObject(path);

        try {
            double budget = root.getDouble("budget");
            if (budget <= 0) {
                throw new JSONException("Budget value should be more than 0. Using default budget instead.");
            }
            if (budget > Command.MAX_INPUT_VAL) {
                throw new JSONException("Budget value should be no more than " + Command.MAX_INPUT_VAL +
                        ". Using default budget instead.");
            }
            ExpenseManager.initExpenseManager(budget);
            LOGGER.log(Level.INFO, "budget loaded: " + budget);
        } catch (JSONException e) {
            ExceptionHandler.handleJSONException(e);
        }

        String currencyName = null;
        try {
            currencyName = root.getString("currency");
            Currency currency = Currency.valueOf(currencyName);
            ExpenseManager.setBaseCurrency(currency);
        } catch (JSONException e) {
            ExceptionHandler.handleJSONException(e);
        } catch (IllegalArgumentException e) {
            assert currencyName != null;
            Ui.printMessage("Unrecognized currency: " + currencyName + ". Using SGD instead.");
        }

        JSONArray categoriesArr = root.getJSONArray("categories");
        for (int i = 0; i < categoriesArr.length(); i++) {
            String category = null;
            try {
                category = categoriesArr.getString(i);
                ExpenseManager.createCategory(category);
            } catch (InvalidArgumentException e) {
                ExceptionHandler.handleJSONException(new JSONException(
                        "Category \"" + category + "\" already exists. Skipping."));
            } catch (JSONException e) {
                ExceptionHandler.handleJSONException(e);
            }
        }

        JSONArray expensesArr = root.getJSONArray("expenses");
        for (int i = 0; i < expensesArr.length(); i++) {
            try {
                JSONObject expObj = expensesArr.getJSONObject(i);
                ExpenseManager.addExpense(expObj);
            } catch (JSONException e) {
                ExceptionHandler.handleJSONException(e);
            }
        }
        Ui.printMessage("Loaded data from file: " + path);
    }
}
