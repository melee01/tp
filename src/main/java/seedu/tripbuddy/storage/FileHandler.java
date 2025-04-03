package seedu.tripbuddy.storage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tripbuddy.dataclass.Expense;
import seedu.tripbuddy.framework.ExpenseManager;

public class FileHandler {
    private static final String FILE_PATH = "tripbuddy_data.json";

    private static JSONObject readJsonObject(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
        }
        return new JSONObject(content.toString());
    }

    private static void writeJsonObject(String path, JSONObject data) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data.toString(4));
        }
    }

    public static void saveData() {
        JSONObject root = new JSONObject();
        root.put("budget", ExpenseManager.getBudget());
        root.put("totalExpense", ExpenseManager.getTotalExpense());

        JSONArray categoriesArr = new JSONArray();
        for (String category : ExpenseManager.getCategories()) {
            categoriesArr.put(category);
        }
        root.put("categories", categoriesArr);

        JSONArray expensesArr = new JSONArray();
        for (Expense expense : ExpenseManager.getExpenses()) {
            JSONObject expObj = new JSONObject();
            expObj.put("name", expense.getName());
            expObj.put("amount", expense.getAmount());
            expObj.put("category", expense.getCategory());
            expObj.put("dateTime", expense.getDateTimeString());
            expensesArr.put(expObj);
        }
        root.put("expenses", expensesArr);

        try {
            writeJsonObject(FILE_PATH, root);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void loadData() {
        try {
            JSONObject root = readJsonObject(FILE_PATH);
            if (root == null) {
                return;
            }

            double budget = root.getDouble("budget");
            ExpenseManager.initExpenseManager(budget);

            JSONArray categoriesArr = root.getJSONArray("categories");
            Set<String> categories = new HashSet<>();
            for (int i = 0; i < categoriesArr.length(); i++) {
                categories.add(categoriesArr.getString(i));
            }
            ExpenseManager.setCategories(categories);

            JSONArray expensesArr = root.getJSONArray("expenses");
            List<Expense> expenses = new ArrayList<>();
            for (int i = 0; i < expensesArr.length(); i++) {
                JSONObject expObj = expensesArr.getJSONObject(i);
                String name = expObj.getString("name");
                double amount = expObj.getDouble("amount");
                String category = expObj.optString("category", null);
                String dateTimeStr = expObj.getString("dateTime");

                Expense expense = new Expense(name, amount, category, dateTimeStr);
                expenses.add(expense);
            }
            ExpenseManager.setExpenses(expenses);

        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
