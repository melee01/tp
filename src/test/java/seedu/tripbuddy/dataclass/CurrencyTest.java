package seedu.tripbuddy.dataclass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyTest {

    @Test
    void testGetFullName() {
        // Verify that getFullName returns the correct name
        assertEquals("USD", Currency.USD.getFullName());
        assertEquals("SGD", Currency.SGD.getFullName());
        assertEquals("EUR", Currency.EUR.getFullName());
    }

    @Test
    void testConvert() {
        // For SGD, with a rate of 1, converting 100 should return 100.
        assertEquals(100.0, Currency.SGD.convert(100.0), 0.0001);
        // For USD, conversion should multiply the amount by 1.342.
        assertEquals(134.2, Currency.USD.convert(100.0), 0.0001);
    }

    @Test
    void testGetAndSetRate() {
        // Store original rate for later restoration (if needed)
        double originalRate = Currency.JPY.getRate();
        Currency.JPY.setRate(0.01);
        assertEquals(0.01, Currency.JPY.getRate(), 0.0001);
        Currency.JPY.setRate(originalRate);
    }

    @Test
    void testValueOfValid() {
        // Check that valueOf returns the correct enum constant
        Currency currency = Currency.valueOf("EUR");
        assertEquals(Currency.EUR, currency);
    }

    @Test
    void testValueOfInvalid() {
        // Check that valueOf throws IllegalArgumentException for an invalid name
        assertThrows(IllegalArgumentException.class, () -> Currency.valueOf("INVALID"));
    }

    @Test
    void testValueOfNull() {
        // Check that valueOf throws NullPointerException for a null argument
        assertThrows(NullPointerException.class, () -> Currency.valueOf(null));
    }
}
