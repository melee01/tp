package seedu.tripbuddy.dataclass;
public enum Currency {
    IDR("IDR", 0.00008101),
    LAK("LAK", 0.00006194),
    MYR("MYR", 0.3025),
    PHP("PHP", 0.02338),
    SGD("SGD", 1),
    THB("THB", 0.03951),
    VND("VND", 0.00005245),
    USD("USD", 1.342 ),
    EUR("EUR", 1.453 ),
    JPY("JPY", 0.008953),
    GBP("GBP", 1.736);

    private final String name;
    private double rate;

    Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getFullName() {
        return name;
    }

    public double convert(double amount) {
        return amount * rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
