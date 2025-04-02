package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;

public class InputHandlerTest {
    @Test
    public void isQuitCommandTest_setBudget_expectFalse() {
        String userInput = "set-budget 135";
        boolean result = InputHandler.isQuitCommand(userInput);
        assertFalse(result);
    }

    @Test
    public void isQuitCommandTest_quit_expectTrue() {
        String userInput = "quit";
        boolean result = InputHandler.isQuitCommand(userInput);
        assertTrue(result);
    }

    @Test
    public void isQuitCommandTest_quitWithExtra_expectTrue() {
        String userInput = "quit 123";
        boolean result = InputHandler.isQuitCommand(userInput);
        assertTrue(result);
    }

    @Test
    public void handleUserInputTest_validInputs() {
        assertAll(
                () -> InputHandler.handleUserInput("tutorial"),
                () -> InputHandler.handleUserInput("set-budget 381"),
                () -> InputHandler.handleUserInput("adjust-budget 432"),
                () -> InputHandler.handleUserInput("view-budget"),
                () -> InputHandler.handleUserInput("create-category accommodation"),
                () -> InputHandler.handleUserInput("add-expense greek-meal 10"),
                () -> InputHandler.handleUserInput("set-category greek-meal food"),
                () -> InputHandler.handleUserInput("delete-expense greek-meal")
        );
    }

    @Test
    public void handleUserInputWithInvalidInputsTest() {
        // TODO: extract to other tests
        assertAll(
                () -> InputHandler.handleUserInput("set-budget"),
                () -> InputHandler.handleUserInput("create-category"),
                () -> InputHandler.handleUserInput("add-expense greek-meal twenty"),
                () -> InputHandler.handleUserInput("set-category greek-meal"),
                () -> InputHandler.handleUserInput("delete-expense greek-meal")
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
