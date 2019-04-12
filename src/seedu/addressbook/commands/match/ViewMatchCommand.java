package seedu.addressbook.commands.match;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.match.ReadOnlyMatch;

/**
 * Shows all details of the match identified using the last displayed index.
 */

public class ViewMatchCommand extends Command {

    public static final String COMMAND_WORD = "viewmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the match "
            + "identified by the index number in the last shown match listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_VIEW_MATCH_DETAILS = "Viewing Match: %1$s";


    public ViewMatchCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMatch target = getTargetMatch();
            if (!addressBook.containsMatch(target)) {
                return new CommandResult(Messages.MESSAGE_MATCH_NOT_IN_LEAGUE_TRACKER);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_MATCH_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MATCH_DISPLAYED_INDEX);
        }
    }
}