package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;


/**
 * Deletes a match identified using it's last displayed index from the address book.
 */
public class DeleteMatchCommand extends Command {

    public static final String COMMAND_WORD = "deletematch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the match identified by the index number used in the last match listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MATCH_SUCCESS = "Deleted Match: %1$s";


    public DeleteMatchCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMatch target = getTargetMatch();
            addressBook.removeMatch(target);
            return new CommandResult(String.format(MESSAGE_DELETE_MATCH_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MATCH_DISPLAYED_INDEX);
        } catch (MatchNotFoundException mnfe) {
            return new CommandResult(Messages.MESSAGE_MATCH_NOT_IN_ADDRESSBOOK);
        }
    }

}