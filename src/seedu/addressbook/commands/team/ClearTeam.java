package seedu.addressbook.commands.team;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the team List.
 */
public class ClearTeam extends Command {
    public static final String COMMAND_WORD = "clearteam";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears team list permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "team list has been cleared!";

    @Override
    public CommandResult execute() {
        addressBook.clearTeam();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
