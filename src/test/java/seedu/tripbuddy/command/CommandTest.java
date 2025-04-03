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
}
