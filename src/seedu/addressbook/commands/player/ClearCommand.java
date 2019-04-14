package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the player list in league tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clearPlayer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clear players in league tracker permanently.\n\t"
            + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_SUCCESS = "League Tracker player list has been cleared!";

    @Override
    public CommandResult execute() {
        addressBook.clearPlayer();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
