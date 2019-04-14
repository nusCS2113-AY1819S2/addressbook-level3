package seedu.addressbook.commands.match;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.MatchDate;
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

/**
 * Updates a match identified using it's last displayed index from the address book.
 */
public class UpdateMatchCommand extends Command {

    public static final String COMMAND_WORD = "updatematch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Updates the details of the match identified by the index number used in the displayed match list.\n"
            + "(listmatch must be used before this command to retrieve index for team to be deleted)\n\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "h/HOMETICKETSALES "
            + "a/AWAYTICKETSALES "
            + "[g/GOALSCORERS] "
            + "[o/OWNGOALSCORERS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "h/1395 a/592 g/John g/Jack o/Jane o/Bob\n";

    public static final String MESSAGE_UPDATE_MATCH_SUCCESS = "Updated match: %1$s";

    private final UpdateMatchDescriptor updateMatchDescriptor;

    /**
     * Constructs an UpdateMatchCommand
     *
     * @param targetVisibleIndex Index of match in match list seen by user.
     * @param homeSales Match day ticket sales for home team.
     * @param awaySales Match day ticket sales for away team.
     * @param goalScorers List of goal scorers in the match
     * @param ownGoalScorers List of own goal scorers in the match
     * @throws IllegalValueException when player names of goal scorers and own goal scorers are invalid.
     */
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
            updatedMatch.setScore(addressBook.computeScore(target, updatedMatch));
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
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        } catch (UniquePlayerList.PlayerNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE);
        }
    }

    /**
     * Creates and returns a match with the details of target updated with updateMatchDescriptor.
     */
    private static Match createUpdateMatch(ReadOnlyMatch target,
                                         UpdateMatchDescriptor updateMatchDescriptor) {
        MatchDate date = target.getDate();
        TeamName home = target.getHome();
        TeamName away = target.getAway();
        TicketSales homeSales = updateMatchDescriptor.getHomeSales();
        TicketSales awaySales = updateMatchDescriptor.getAwaySales();
        List<Name> goalScorers = updateMatchDescriptor.getGoalScorers();
        List<Name> ownGoalscorers = updateMatchDescriptor.getOwnGoalScorers();
        return new Match(date, home, away, homeSales, awaySales, goalScorers, ownGoalscorers, new Score(""));
    }
}
