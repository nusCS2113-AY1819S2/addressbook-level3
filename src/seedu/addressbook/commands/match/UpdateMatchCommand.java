package seedu.addressbook.commands.match;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Date;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.Score;
import seedu.addressbook.data.match.TicketSales;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;
import seedu.addressbook.data.match.UniqueMatchList.MatchUpdatedException;
import seedu.addressbook.data.match.UpdateMatchDescriptor;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.team.TeamName;
import seedu.addressbook.data.team.UniqueTeamList.TeamNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Updates a match identified using it's last displayed index from the address book.
 */
public class UpdateMatchCommand extends Command {

    public static final String COMMAND_WORD = "updatematch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Updates the match identified by the index number used in the last match listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UPDATE_MATCH_SUCCESS = "Updated match: %1$s";

    private final UpdateMatchDescriptor updateMatchDescriptor;


    public UpdateMatchCommand(int targetVisibleIndex, String homeSales, String awaySales,
                              List<String> goalScorers, List<String> ownGoalScorers) throws IllegalValueException {
        super(targetVisibleIndex);
        final List<Name> goalScorerList = new ArrayList<>();
        for (String playerName : goalScorers) {
            goalScorerList.add(new Name(playerName));
        }
        final List<Name> ownGoalScorerList = new ArrayList<>();
        for (String playerName : ownGoalScorers) {
            ownGoalScorerList.add(new Name(playerName));
        }

        this.updateMatchDescriptor = new UpdateMatchDescriptor(homeSales, awaySales,
                                                                goalScorerList, ownGoalScorerList);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMatch target = getTargetMatch();
            Match updatedMatch = createUpdateMatch(target, updateMatchDescriptor);
            addressBook.updateMatch(target, updatedMatch);
            return new CommandResult(String.format(MESSAGE_UPDATE_MATCH_SUCCESS, updatedMatch));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MATCH_DISPLAYED_INDEX);
        } catch (MatchNotFoundException mnfe) {
            return new CommandResult(Messages.MESSAGE_MATCH_NOT_IN_LEAGUE_TRACKER);
        } catch (MatchUpdatedException mue) {
            return new CommandResult(Messages.MESSAGE_MATCH_UPDATED_BEFORE);
        } catch (TeamNotFoundException tnfe) {
            return new CommandResult(Messages.MESSAGE_TEAM_NOT_IN_LEAGUE_TRACKER);
        } catch (UniquePlayerList.PlayerNotInTeamException pnite) {
            return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_TEAM);
        }
    }

    /**
     * Creates and returns a match with the details of target updated with updateMatchDescriptor.
     */
    private static Match createUpdateMatch(ReadOnlyMatch target,
                                         UpdateMatchDescriptor updateMatchDescriptor) {
        Date date = target.getDate();
        TeamName home = target.getHome();
        TeamName away = target.getAway();
        TicketSales homeSales = updateMatchDescriptor.getHomeSales();
        TicketSales awaySales = updateMatchDescriptor.getAwaySales();
        List<Name> goalScorers = updateMatchDescriptor.getGoalScorers();
        List<Name> ownGoalscorers = updateMatchDescriptor.getOwnGoalScorers();
        return new Match(date, home, away, homeSales, awaySales, goalScorers, ownGoalscorers, new Score(""));
    }
}
