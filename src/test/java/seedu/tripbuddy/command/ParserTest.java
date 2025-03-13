package seedu.tripbuddy.command;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.framework.Ui;

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
