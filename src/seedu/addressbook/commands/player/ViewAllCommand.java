package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.player.ReadOnlyPlayer;


/**
 * Shows all details of the player identified using the last displayed index.
 * Private contact details are shown.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "displayProfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the player "
            + "identified by the index number in the last shown player listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_VIEW_PLAYER_DETAILS = "Viewing player: %1$s";


    public ViewAllCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPlayer target = getTargetPlayer();
            if (!addressBook.containsPlayer(target)) {
                return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_PLAYER_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        }
    }
}
