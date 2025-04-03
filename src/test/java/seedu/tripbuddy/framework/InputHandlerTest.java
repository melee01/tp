package seedu.tripbuddy.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;

public class InputHandlerTest {

    @Test
    void handleUserInputTutorialTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            InputHandler.handleUserInput("tutorial");
            String output = outContent.toString();
            assertTrue(output.contains("Welcome to the tutorial of TripBuddy"));
            assertTrue(output.contains("set-budget"));
            assertTrue(output.contains("add-expense"));
        } finally {
            System.setOut(originalOut);
        }
    }

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
                () -> InputHandler.handleUserInput("add-expense greek-meal -a 10"),
                () -> InputHandler.handleUserInput("set-category greek-meal -c food"),
                () -> InputHandler.handleUserInput("delete-expense greek-meal")
        );
    }

    @Test
    public void handleUserInputTest_invalidInputs() {
        assertAll(
                () -> InputHandler.handleUserInput("tuutorial"),
                () -> InputHandler.handleUserInput("set-budget"),
                () -> InputHandler.handleUserInput("create-category"),
                () -> InputHandler.handleUserInput("add-expense greek-meal -a twenty"),
                () -> InputHandler.handleUserInput("set-category greek-meal"),
                () -> InputHandler.handleUserInput("delete-expense greek-meal")
        );
    }

    @Test
    public void handleUserInputTest_edgeCases() {
        assertAll(
                () -> InputHandler.handleUserInput("set-budget 0"),
                () -> InputHandler.handleUserInput("set-budget -100"),
                () -> InputHandler.handleUserInput("add-expense item -a 0"),
                () -> InputHandler.handleUserInput("add-expense item -a -5"),
                () -> InputHandler.handleUserInput("create-category Meals"),
                () -> InputHandler.handleUserInput("create-category "),
                () -> InputHandler.handleUserInput("add-expense Lunch -a 15 with extra text"),
                () -> InputHandler.handleUserInput("delete-expense "),
                () -> InputHandler.handleUserInput("view-expenses"),
                () -> InputHandler.handleUserInput("      view-expenses")
        );
    }
}
