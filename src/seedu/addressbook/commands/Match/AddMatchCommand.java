package seedu.addressbook.commands.Match;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Away;
import seedu.addressbook.data.match.Date;
import seedu.addressbook.data.match.Home;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.tag.Tag;

/**
 * Adds a match to the address book.
 */
public class AddMatchCommand extends Command {

    public static final String COMMAND_WORD = "addmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a match to the address book. "
            + "Parameters: DATE h/HOMETEAM a/AWAYTEAM [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " 17 MAR 2019 h/West Ham a/Huddersfield";

    public static final String MESSAGE_SUCCESS = "New match added: %1$s";
    public static final String MESSAGE_DUPLICATE_MATCH = "This match already exists in the address book";

    private final Match toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddMatchCommand(String date,
                      String home,
                      String away,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Match(
                new Date(date),
                new Home(home),
                new Away(away),
                tagSet
        );
    }

    public AddMatchCommand(Match toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyMatch getMatch() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addMatch(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueMatchList.DuplicateMatchException dme) {
            return new CommandResult(MESSAGE_DUPLICATE_MATCH);
        }
    }

}
