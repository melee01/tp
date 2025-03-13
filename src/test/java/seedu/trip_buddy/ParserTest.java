package seedu.trip_buddy;

import org.junit.jupiter.api.Test;
import seedu.trip_buddy.command.Parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ParserTest {
    @Test
    public void isValidUserInputTest() {
        String userInput = "set-budget 135";
        Parser parser = new Parser();
        boolean result = parser.isValidUserInput(userInput);
        assertTrue(result);

        userInput = "quit";
        result = parser.isValidUserInput(userInput);
        assertFalse(result);
    }
}
