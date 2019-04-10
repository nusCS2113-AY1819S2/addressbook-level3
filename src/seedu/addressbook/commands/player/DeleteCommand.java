package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.UniquePlayerList;


/**
 * Deletes a player identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deletePlayer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the player identified by the index number used in the last player listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_DELETE_PLAYER_SUCCESS = "Deleted Player: %1$s";


    public DeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPlayer target = getTargetPlayer();
            addressBook.removePlayer(target);
            return new CommandResult(String.format(MESSAGE_DELETE_PLAYER_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        } catch (UniquePlayerList.PlayerNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE);
        }
    }

}
