package seedu.trip_buddy.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    @Test
    void setBudget_set135() {
        CommandHandler executor = new CommandHandler();
        executor.handleSetBudget(135);
        String message = executor.handleViewBudget();
        assertEquals("Your current budget is $135.", message);
    }
}
