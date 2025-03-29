package seedu.tripbuddy.command;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.framework.Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        userInput = "quit 123";
        result = parser.isQuitCommand(userInput);
        assertTrue(result);
    }

    @Test
    public void handleUserInputWithValidInputsTest() {
        Parser parser = new Parser(new Ui());
        assertAll(
                () -> parser.handleUserInput("tutorial"),
                () -> parser.handleUserInput("set-budget 381"),
                () -> parser.handleUserInput("adjust-budget 432"),
                () -> parser.handleUserInput("view-budget"),
                () -> parser.handleUserInput("create-category accommodation"),
                () -> parser.handleUserInput("add-expense greek-meal 10"),
                () -> parser.handleUserInput("set-category greek-meal food"),
                () -> parser.handleUserInput("delete-expense greek-meal")
        );
    }

    @Test
    public void handleUserInputWithInvalidInputsTest() {
        Parser parser = new Parser(new Ui());
        assertAll(
                () -> parser.handleUserInput("tuutorial"),
                () -> parser.handleUserInput("set-budget"),
                () -> parser.handleUserInput("create-category"),
                () -> parser.handleUserInput("add-expense greek-meal twenty"),
                () -> parser.handleUserInput("set-category greek-meal"),
                () -> parser.handleUserInput("delete-expense greek-meal")
        );
    }
}
