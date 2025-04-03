package seedu.tripbuddy.command;

import org.junit.jupiter.api.Test;
import seedu.tripbuddy.exception.InvalidKeywordException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ParserTest {

    @Test
    void parseCommandTest_invalidKeyword_expectInvalidKeywordException() {
        assertThrows(InvalidKeywordException.class, () -> Parser.parseCommand("lol"));
    }

    @Test
    void parseCommandTest_keywordOnly() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand("add-expense");
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(0, cmd.getOptList().size(), "optList");
    }

    @Test
    void parseCommandTest_keywordWithExtraSpace() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand(" add-expense  ");
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(0, cmd.getOptList().size(), "optList");
    }

    @Test
    void parseCommandTest_noOpt() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand("add-expense  bb ccc d");
        ArrayList<Option> optList = cmd.getOptList();
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(1, optList.size(), "optList");
        assertTrue(optList.get(0).opt().isEmpty(), "opt");
        assertEquals("bb ccc d", optList.get(0).val(), "val");
    }

    @Test
    void parseCommandTest_noTokensBeforeFirstOpt() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand("add-expense -b bb");
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(1, cmd.getOptList().size(), "optList");
    }

    @Test
    void parseCommandTest_extraTokensBeforeFirstOpt() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand("add-expense aa -b bb -c cc");
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(3, cmd.getOptList().size(), "optList");
    }

    @Test
    void parseCommandTest_hasOptWithoutVal() throws InvalidKeywordException {
        Command cmd = Parser.parseCommand("add-expense aa -d2 -b bb -d1 -c cc");
        assertEquals(Keyword.ADD_EXPENSE, cmd.getKeyword(), "keyword");
        assertEquals(5, cmd.getOptList().size(), "optList");
    }
}
