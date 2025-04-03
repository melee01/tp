package seedu.tripbuddy.command;

import seedu.tripbuddy.exception.InvalidArgumentException;
import seedu.tripbuddy.exception.MissingOptionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores a command line. Contains a {@link Keyword} and a list of {@link Option}.
 */
public class Command {

    private final Keyword keyword;
    private final ArrayList<Option> optList;
    private final HashMap<String, String> optMap;

    public Command(Keyword keyword) {
        this.keyword = keyword;
        this.optList = new ArrayList<>();
        this.optMap = new HashMap<>();
    }

    @Override
    public String toString() {
        if (optList.isEmpty()) {
            return keyword.toString();
        }

        StringBuilder builder = new StringBuilder();
        for (Option opt : optList) {
            builder.append(' ').append(opt);
        }
        return keyword.toString() + builder;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public ArrayList<Option> getOptList() {
        return optList;
    }

    /**
     * Appends a new option to the option list
     */
    public void addOption(Option opt) {
        optList.add(opt);
        optMap.put(opt.opt(), opt.val());
    }

    public boolean hasOpt(String opt) {
        return optMap.containsKey(opt);
    }

    /**
     * Returns the value of a given option if exists.
     */
    public String getOpt(String opt) throws MissingOptionException {
        if (!optMap.containsKey(opt)) {
            throw new MissingOptionException(opt);
        }
        return optMap.get(opt);
    }

    public int getOptCount() {
        return optList.size();
    }
}
