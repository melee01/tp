package seedu.tripbuddy.command;

import seedu.tripbuddy.exception.InvalidKeywordException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    /**
     * Checks if a {@code String} is an option expression.
     * <p>Returns {@code true} if the string starts with {@code '-'}.
     */
    private static boolean isOpt(String s) {
        return s.startsWith("-");
    }

    /**
     * Parses an input line into {@link Command}.
     */
    public static Command parseCommand(String cmdInput) throws InvalidKeywordException {
        LOGGER.log(Level.INFO, "Start parsing: \"" + cmdInput + '"');

        String[] tokens = cmdInput.strip().split(" ");
        Command cmd = null;

        String keywordString = tokens[0];
        for (Keyword i : Keyword.values()) {
            if (keywordString.equals(i.toString())) {
                cmd = new Command(i);
                break;
            }
        }

        // Not a legit keyword
        if (cmd == null) {
            throw new InvalidKeywordException(keywordString);
        }

        int numTokens = tokens.length;
        if (numTokens == 1) {
            return cmd;
        }

        String opt = "";
        StringBuilder val = new StringBuilder();
        for (int i = 1; i < numTokens; ++i) {
            if (!isOpt(tokens[i])) {
                val.append(tokens[i]).append(' ');
                continue;
            }
            // Has extra tokens before the first opt-val pair
            if (i > 1) {
                Option newOpt = new Option(opt, val.toString().strip());
                cmd.addOption(newOpt);
                LOGGER.log(Level.INFO, "New option: \"" + newOpt + '"');
            }
            opt = tokens[i].substring(1);
            val = new StringBuilder();
        }

        Option newOpt = new Option(opt, val.toString().strip());
        cmd.addOption(newOpt);
        LOGGER.log(Level.INFO, "New option: \"" + newOpt + '"');

        LOGGER.log(Level.INFO, "End parsing: \"" + cmd + '"');
        return cmd;
    }
}
