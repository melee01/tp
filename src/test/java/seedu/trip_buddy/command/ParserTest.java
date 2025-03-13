package seedu.trip_buddy.command;

import org.junit.jupiter.api.Test;
import seedu.trip_buddy.command.Parser;
import seedu.trip_buddy.framework.Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ParserTest {
    @Test
    public void isQuitCommandTest() {
        String userInput = "set-budget 135";
        Parser parser = new Parser(new Ui());
        boolean result = parser.isQuitCommand(userInput);
        assertFalse(result);

        userInput = "quit";
        result = parser.isQuitCommand(userInput);
        assertTrue(result);
    }
}
