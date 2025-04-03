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
    public void isQuitCommandTest_mixedCaseQuit_expectFalse() {
        String userInput = "QuIt";
        boolean result = Parser.isQuitCommand(userInput);
        assertFalse(result);
    }

    @Test
    public void isQuitCommandTest_extraSpaces_expectFalse() {
        String userInput = "  quit   ";
        boolean result = Parser.isQuitCommand(userInput);
        assertFalse(result);
    }

    @Test
    public void handleUserInputTest_validInputs() {
        assertAll(
                () -> Parser.handleUserInput("tutorial"),
                () -> Parser.handleUserInput("set-budget 400"),
                () -> Parser.handleUserInput("view-budget"),
                () -> Parser.handleUserInput("create-category Accommodation"),
                () -> Parser.handleUserInput("add-expense greek_meal 10"),
                () -> Parser.handleUserInput("set-category greek_meal meals"),
                () -> Parser.handleUserInput("delete-expense greek_meal"),
                () -> Parser.handleUserInput("adjust-budget 500")
        );
    }

    @Test
    public void handleUserInputWithInvalidInputsTest() {
        // TODO: extract to other tests
        assertAll(
                () -> Parser.handleUserInput("set-budget "),
                () -> Parser.handleUserInput("create-category "),
                () -> Parser.handleUserInput("add-expense greek_meal twenty-one"),
                () -> Parser.handleUserInput("set-category greek_meal"),
                () -> Parser.handleUserInput("delete-expense greek_meal")
        );
    }

    @Test
    public void handleUserInputTest_edgeCases() {
        assertAll(
                () -> Parser.handleUserInput("set-budget 0"),
                () -> Parser.handleUserInput("set-budget -100"),
                () -> Parser.handleUserInput("add-expense item 0"),
                () -> Parser.handleUserInput("add-expense item -5"),
                () -> Parser.handleUserInput("create-category Meals"),
                () -> Parser.handleUserInput("create-category "),
                () -> Parser.handleUserInput("add-expense Lunch 15 with extra text"),
                () -> Parser.handleUserInput("delete-expense "),
                () -> Parser.handleUserInput("view-expenses"),
                () -> Parser.handleUserInput("      view-expenses")
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
