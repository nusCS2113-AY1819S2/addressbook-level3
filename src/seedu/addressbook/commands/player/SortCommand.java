package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Sorts all players in the league tracker.
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Sorts all players in the league tracker in ascending alphabetical order.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "League player list has been sorted!";

    @Override
    public CommandResult execute() {
        addressBook.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

