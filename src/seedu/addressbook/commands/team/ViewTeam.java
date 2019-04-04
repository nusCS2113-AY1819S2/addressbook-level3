package seedu.addressbook.commands.team;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * Shows all details of the team identified using the last displayed index.
 */

public class ViewTeam extends Command {

    public static final String COMMAND_WORD = "viewteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the team "
            + "identified by the index number in the last shown team listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_TEAM_DETAILS = "Viewing Team: %1$s";


    public ViewTeam(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTeam target = getTargetTeam();
            if (!addressBook.containsTeam(target)) {
                return new CommandResult(Messages.MESSAGE_TEAM_NOT_IN_LEAGUE_TRACKER);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_TEAM_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
    }
}
