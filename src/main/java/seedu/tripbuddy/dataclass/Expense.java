package seedu.tripbuddy.dataclass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores info of a travel expense.
 * */
public class Expense {

    private String name;
    private double amount;
    private String category;
    private LocalDateTime dateTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.category = null;
        this.dateTime = LocalDateTime.now();
    }

    public Expense(String name, double amount, String category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.dateTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        String dateTimeStr = dateTime.format(FORMATTER);
        if (category == null) {
            return "name: " + name + ", amount: " + String.format("%.2f", amount) + ", date: " + dateTimeStr;
        }
        return "name: " + name + ", amount: " + String.format("%.2f", amount)
                + ", category: " + category + ", date: " + dateTimeStr;
    }
}
