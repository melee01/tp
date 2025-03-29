package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.exception.InvalidArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandHandlerTest {

    @Test
    void addExpense_negativeAmount_expectInvalidArgumentException() {
        CommandHandler handler = new CommandHandler();
        assertThrows(InvalidArgumentException.class, () -> handler.handleAddExpense("a", -1));
    }

    @Test
    void setBudget_set135() throws InvalidArgumentException {
        CommandHandler handler = new CommandHandler();
        handler.handleSetBudget(135);
        String message = handler.handleViewBudget();
        assertEquals("The original budget you set was $135.\n" +
                "So far, you have spent $0.\n" +
                "This leaves you with a remaining budget of $135.", message);
    }
}
