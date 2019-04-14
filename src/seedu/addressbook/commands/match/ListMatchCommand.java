package seedu.addressbook.commands.match;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.match.ReadOnlyMatch;

/**
 * Lists all matches in the league tracker to the user.
 */
public class ListMatchCommand extends Command {

    public static final String COMMAND_WORD = "listmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all matches in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + "\n";


    @Override
    public CommandResult execute() {
        List<ReadOnlyMatch> allMatches = addressBook.getAllMatches().immutableListView();
        return new CommandResult(getMessageForMatchListShownSummary(allMatches), null, null, allMatches, null);
    }
}
