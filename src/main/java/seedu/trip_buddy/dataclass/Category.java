package seedu.trip_buddy.dataclass;

/**
 * Acts as an optional tag for {@link Expense}.
 * */
public class Category {

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
