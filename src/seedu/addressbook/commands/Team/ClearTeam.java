package seedu.addressbook.commands.Team;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the Team List.
 */
public class ClearTeam extends Command {
    public static final String COMMAND_WORD = "clearTeam";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears Team List permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Team List has been cleared!";

    @Override
    public CommandResult execute() {
        addressBook.clearTeam();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
