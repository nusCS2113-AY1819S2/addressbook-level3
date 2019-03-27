package seedu.addressbook.commands;

import seedu.addressbook.commands.finance.FinanceCommand;
import seedu.addressbook.commands.finance.ListFinanceCommand;
import seedu.addressbook.commands.match.AddMatchCommand;
import seedu.addressbook.commands.match.ClearMatchCommand;
import seedu.addressbook.commands.match.DeleteMatchCommand;
import seedu.addressbook.commands.match.FindMatchCommand;
import seedu.addressbook.commands.match.ListMatchCommand;
import seedu.addressbook.commands.player.*;
import seedu.addressbook.commands.team.AddTeam;
import seedu.addressbook.commands.team.ClearTeam;
import seedu.addressbook.commands.team.DeleteTeam;
import seedu.addressbook.commands.team.EditTeam;
import seedu.addressbook.commands.team.FindTeam;
import seedu.addressbook.commands.team.ListTeam;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = AddCommand.MESSAGE_USAGE
            + "\n" + DeleteCommand.MESSAGE_USAGE
            + "\n" + ClearCommand.MESSAGE_USAGE
            + "\n" + FindCommand.MESSAGE_USAGE
            + "\n" + ListCommand.MESSAGE_USAGE
            + "\n" + SortCommand.MESSAGE_USAGE
            + "\n" + AddTeam.MESSAGE_USAGE
            + "\n" + EditTeam.MESSAGE_USAGE
            + "\n" + DeleteTeam.MESSAGE_USAGE
            + "\n" + ClearTeam.MESSAGE_USAGE
            + "\n" + FindTeam.MESSAGE_USAGE
            + "\n" + ListTeam.MESSAGE_USAGE
            + "\n" + ViewAllCommand.MESSAGE_USAGE
            + "\n" + FinanceCommand.MESSAGE_USAGE
            + "\n" + ListFinanceCommand.MESSAGE_USAGE
            + "\n" + AddMatchCommand.MESSAGE_USAGE
            + "\n" + DeleteMatchCommand.MESSAGE_USAGE
            + "\n" + ClearMatchCommand.MESSAGE_USAGE
            + "\n" + FindMatchCommand.MESSAGE_USAGE
            + "\n" + ListMatchCommand.MESSAGE_USAGE
            + "\n" + HelpCommand.MESSAGE_USAGE
            + "\n" + ExitCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
