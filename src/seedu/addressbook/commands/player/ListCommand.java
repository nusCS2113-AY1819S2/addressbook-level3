package seedu.addressbook.commands.player;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.player.ReadOnlyPlayer;


/**
 * Lists all players in the league to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all players in the league as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + "\n";


    @Override
    public CommandResult execute() {
        List<ReadOnlyPlayer> allPlayers = addressBook.getAllPlayers().immutableListView();
        return new CommandResult(getMessageForPlayerListShownSummary(allPlayers), allPlayers, null, null, null);
    }
}
