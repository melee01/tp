package seedu.tripbuddy.dataclass;
public enum Currency {
    MYR("MYR", 3.29),
    PHP("PHP", 42.63),
    SGD("SGD", 1),
    THB("THB", 25.52),
    USD("USD", 0.74),
    EUR("EUR", 0.68),
    JPY("JPY", 109.11),
    AUD("AUD", 1.23),
    CAD("CAD", 1.06),
    CNY("CNY", 5.41),
    HKD("HKD", 5.77),
    INR("INR", 63.50),
    NZD("NZD", 1.33),
    CHF("CHF", 0.64),
    TWD("TWD", 24.63),
    ZAR("ZAR", 14.18),
    GBP("GBP", 0.58);

    private final String name;
    private double rate;

    Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getFullName() {
        return name;
    }

    /**
     * Convert an amount of base currency to this currency.
     */
    public double convert(double amount) {
        return amount * rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Calculates new exchanges rates to the give new base currency.
     */
    public static void setBaseCurrency(Currency newBase) {
        double newBaseRate = newBase.rate;
        for (Currency c : Currency.values()) {
            c.rate /= newBaseRate;
        }
    }

    public String getFormattedAmount(double amount) {
        return String.format("%.2f", amount) + " " + name;
    }
}
