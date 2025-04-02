package seedu.tripbuddy.command;

import java.util.ArrayList;

/**
 * Stores a command line. Contains a {@link Keyword} and a list of {@link Option}.
 */
public class Command {

    private final Keyword keyword;
    private final ArrayList<Option> optList;

    public Command(Keyword keyword) {
        this.keyword = keyword;
        this.optList = new ArrayList<>();
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

    public void addOption(Option opt) {
        optList.add(opt);
    }
}
