package seedu.tripbuddy.dataclass;

/**
 * Stores info of a travel expense.
 * */
public class Expense {

    private String name;
    private double amount;
    private String category;

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.category = null;
    }

    public Expense(String name, double amount, String category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
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

    @Override
    public String toString() {
        if (category == null) {
            return "name: " + name + ", amount: " + String.format("%.2f", amount);
        }
        return "name: " + name + ", amount: " + String.format("%.2f", amount) + ", category:" + category;
    }
}
