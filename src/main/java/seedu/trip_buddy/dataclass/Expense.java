package seedu.trip_buddy.dataclass;

/**
 * stores info of a travel expense.
 * */
public class Expense {

    private String name;
    private int amount;
    private Category category;

    public Expense(String name, int amount) {
        this.name = name;
        this.amount = amount;
        this.category = null;
    }

    public Expense(String name, int amount, Category category) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
