package seedu.addressbook.commands.team;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * List all teams
 */
public class ListTeam extends Command {

    public static final String COMMAND_WORD = "listTeam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all Teams in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        List<ReadOnlyTeam> allTeams = addressBook.getAllTeams().immutableListView();
        return new CommandResult(getMessageForTeamListShownSummary(allTeams), null, allTeams, null, null);
    }
}
