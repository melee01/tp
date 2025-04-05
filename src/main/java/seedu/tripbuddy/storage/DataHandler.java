package seedu.tripbuddy.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.tripbuddy.dataclass.Currency;
import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.exception.ExceptionHandler;
import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.framework.ExpenseManager;
import seedu.tripbuddy.framework.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataHandler {

    private static final Logger LOGGER = Logger.getLogger("TripBuddy");

    public static void saveData(String path) throws IOException {
        JSONObject root = new JSONObject();
        root.put("currency", ExpenseManager.getBaseCurrency());
        root.put("budget", ExpenseManager.getBudget());
        root.put("totalExpense", ExpenseManager.getTotalExpense());

        LOGGER.log(Level.INFO, "budget converted");

        JSONArray categoriesArr = new JSONArray();
        for (String category : ExpenseManager.getCategories()) {
            categoriesArr.put(category);
        }
        root.put("categories", categoriesArr);

        LOGGER.log(Level.INFO, "categories converted");

        JSONArray expensesArr = new JSONArray();
        for (Expense expense : ExpenseManager.getExpenses()) {
            JSONObject expObj = new JSONObject();
            expObj.put("name", expense.getName());
            expObj.put("amount", expense.getAmount());
            expObj.put("category", expense.getCategory());
            expObj.put("dateTime", expense.getDateTimeString());
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
        Set<String> categories = new HashSet<>();
        for (int i = 0; i < categoriesArr.length(); i++) {
            try {
                categories.add(categoriesArr.getString(i));
            } catch (JSONException e) {
                ExceptionHandler.handleException(e);
            }
        }
        ExpenseManager.setCategories(categories);

        JSONArray expensesArr = root.getJSONArray("expenses");
        List<Expense> expenses = new ArrayList<>();
        for (int i = 0; i < expensesArr.length(); i++) {
            JSONObject expObj = expensesArr.getJSONObject(i);
            try {
                Expense expense = Expense.fromJSON(expObj);
                ExpenseManager.addExpense(expense);
            } catch (JSONException e) {
                ExceptionHandler.handleException(e);
            } catch (InvalidArgumentException e) {
                ExceptionHandler.handleInvalidArgumentException(e);
            }
        }
        Ui.printMessage("Loaded data from file: " + path);
    }
}
