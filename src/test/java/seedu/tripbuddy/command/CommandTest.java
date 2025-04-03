package seedu.tripbuddy.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommandTest {

    Option emptyOpt = new Option("", "aa");
    Option opt = new Option("b", "bb");
    Option optWithoutVal = new Option("d", "");

    @Test
    void toStringTest_noOpt() {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        assertEquals(Keyword.ADD_EXPENSE.toString(), cmd.toString());
    }

    @Test
    void toStringTest_emptyOptOnly() {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        cmd.addOption(emptyOpt);
        assertEquals(Keyword.ADD_EXPENSE + " aa", cmd.toString());
    }

    @Test
    void toStringTest_optOnly() {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        cmd.addOption(opt);
        cmd.addOption(opt);
        assertEquals(Keyword.ADD_EXPENSE + " -b: bb -b: bb", cmd.toString());
    }

    @Test
    void toStringTest() {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        cmd.addOption(emptyOpt);
        cmd.addOption(opt);
        cmd.addOption(optWithoutVal);
        cmd.addOption(opt);
        assertEquals(Keyword.ADD_EXPENSE + " aa -b: bb -d -b: bb", cmd.toString());
    }

    @Test
    void testGetOptAndHasOpt() throws MissingOptionException {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        assertEquals(0, cmd.getOptCount());
        assertFalse(cmd.hasOpt("x"));
        Option optionX = new Option("x", "testValue");
        cmd.addOption(optionX);
        assertTrue(cmd.hasOpt("x"));
        assertEquals("testValue", cmd.getOpt("x"));
        assertEquals(1, cmd.getOptCount());
    }

    @Test
    void testGetOptThrowsMissingOptionException() {
        Command cmd = new Command(Keyword.ADD_EXPENSE);
        assertThrows(MissingOptionException.class, () -> cmd.getOpt("nonexistent"));
    }

    @Test
    void testOptionToString() {
        Option opt1 = new Option("a", "value");
        assertEquals("-a: value", opt1.toString());
        Option opt2 = new Option("", "justValue");
        assertEquals("justValue", opt2.toString());
        Option opt3 = new Option("b", "");
        assertEquals("-b", opt3.toString());
    }
}
