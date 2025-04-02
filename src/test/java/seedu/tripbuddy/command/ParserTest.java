package seedu.tripbuddy.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ParserTest {
    @Test
    public void isQuitCommandTest_setBudget_expectFalse() {
        String userInput = "set-budget 135";
        boolean result = Parser.isQuitCommand(userInput);
        assertFalse(result);
    }

    @Test
    public void isQuitCommandTest_quit_expectTrue() {
        String userInput = "quit";
        boolean result = Parser.isQuitCommand(userInput);
        assertTrue(result);
    }

    @Test
    public void isQuitCommandTest_quitWithExtra_expectTrue() {
        String userInput = "quit 123";
        boolean result = Parser.isQuitCommand(userInput);
        assertTrue(result);
    }

    @Test
    public void handleUserInputTest_validInputs() {
        assertAll(
                () -> Parser.handleUserInput("tutorial"),
                () -> Parser.handleUserInput("set-budget 381"),
                () -> Parser.handleUserInput("adjust-budget 432"),
                () -> Parser.handleUserInput("view-budget"),
                () -> Parser.handleUserInput("create-category accommodation"),
                () -> Parser.handleUserInput("add-expense greek-meal 10"),
                () -> Parser.handleUserInput("set-category greek-meal food"),
                () -> Parser.handleUserInput("delete-expense greek-meal")
        );
    }

    @Test
    public void handleUserInputWithInvalidInputsTest() {
        // TODO: extract to other tests
        assertAll(
                () -> Parser.handleUserInput("set-budget"),
                () -> Parser.handleUserInput("create-category"),
                () -> Parser.handleUserInput("add-expense greek-meal twenty"),
                () -> Parser.handleUserInput("set-category greek-meal"),
                () -> Parser.handleUserInput("delete-expense greek-meal")
        );
    }

/* TODO: update after parser rework
    @Test
    public void handlerUserInput_tuutorial_expectInvalidKeywordException() {
        assertThrows(InvalidKeywordException.class, () -> Parser.handleUserInput("tuutorial"));
    }

    @Test
    public void handlerUserInput_tuutorial_expectInvalidKeywordException() {
        assertThrows(InvalidKeywordException.class, () -> Parser.handleUserInput("add-expense greek-meal twenty"));
    }
*/
}
