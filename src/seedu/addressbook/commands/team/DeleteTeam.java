package seedu.addressbook.commands.team;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * Deletes a team identified using it's last displayed index from the address book.
 */

public class DeleteTeam extends Command {

    public static final String COMMAND_WORD = "delteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the team identified by the index number used in the last team listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted team: %1$s";


    public DeleteTeam(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTeam target = getTargetTeam();
            addressBook.removeTeam(target);
            return new CommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        } catch (UniqueTeamList.TeamNotFoundException tnfe) {
            return new CommandResult(Messages.MESSAGE_TEAM_NOT_IN_LEAGUE_TRACKER);
        }
    }
}
