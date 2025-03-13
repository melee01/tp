package seedu.tripbuddy.dataclass;

/**
 * Stores info of a travel expense.
 * */
public class Expense {

    private String name;
    private int amount;
    private String category;

    public Expense(String name, int amount) {
        this.name = name;
        this.amount = amount;
        this.category = null;
    }

    public Expense(String name, int amount, String category) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
        return name + ", " + amount + ", " + category;
    }
}
