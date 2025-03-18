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
        assertEquals("Your current budget is $135.", message);
    }
}
