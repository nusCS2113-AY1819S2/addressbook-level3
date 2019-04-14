package seedu.addressbook.commands;
import seedu.addressbook.accountmanager.AccountManager;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = "Here is a list of commands:"
            + "\n\n" + AddCommand.MESSAGE_USAGE
            + "\n\n" + DoctorAppointmentsCommand.MESSAGE_USAGE
            + "\n\n" + ApptDateCommand.MESSAGE_USAGE
            + "\n\n" + DeleteCommand.MESSAGE_USAGE
            + "\n\n" + ClearCommand.MESSAGE_USAGE
            + "\n\n" + FindCommand.MESSAGE_USAGE
            + "\n\n" + LengthCommand.MESSAGE_USAGE
            + "\n\n" + ListCommand.MESSAGE_USAGE
            + "\n\n" + ReferCommand.MESSAGE_USAGE
            + "\n\n" + SortCommand.MESSAGE_USAGE
            + "\n\n" + ViewCommand.MESSAGE_USAGE
            + "\n\n" + ViewAllCommand.MESSAGE_USAGE
            + "\n\n" + HelpCommand.MESSAGE_USAGE
            + "\n\n" + ExitCommand.MESSAGE_USAGE
            + "\n\n" + AccountManager.LOGOUT_MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
