package seedu.addressbook.commands.match;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;
import seedu.addressbook.data.player.UniquePlayerList;


/**
 * Deletes a match identified using it's last displayed index from the address book.
 */
public class DeleteMatchCommand extends Command {

    public static final String COMMAND_WORD = "deletematch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the match identified by the index number used in the last match listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_DELETE_MATCH_SUCCESS = "Deleted match: %1$s";


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
            return new CommandResult(Messages.MESSAGE_MATCH_NOT_IN_LEAGUE_TRACKER);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        } catch (UniquePlayerList.PlayerNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE);
        }
    }

}
