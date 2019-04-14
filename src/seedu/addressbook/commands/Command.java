package seedu.addressbook.commands;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

import java.util.List;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.team.ReadOnlyTeam;


/**
 * Represents an executable command.
 */
public abstract class Command {
    protected AddressBook addressBook;
    protected List<? extends ReadOnlyPlayer> relevantPlayers;
    protected List<? extends ReadOnlyMatch> relevantMatches;
    protected List<? extends ReadOnlyTeam> relevantTeams;
    protected List<? extends ReadOnlyFinance> relevantFinances;

    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target player
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of players.
     *
     * @param playersDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPlayerListShownSummary(List<? extends ReadOnlyPlayer> playersDisplayed) {
        return String.format(Messages.MESSAGE_PLAYERS_LISTED_OVERVIEW, playersDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of Matches.
     */
    public static String getMessageForMatchListShownSummary(List<? extends ReadOnlyMatch> matchesDisplayed) {
        return String.format(Messages.MESSAGE_MATCHES_LISTED_OVERVIEW, matchesDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of Teams.
     */
    public static String getMessageForTeamListShownSummary(List<? extends ReadOnlyTeam> teamsDisplayed) {
        return String.format(Messages.MESSAGE_TEAMS_LISTED_OVERVIEW, teamsDisplayed.size());
    }

    public static String getMessageForFinanceListShownSummary(List<? extends ReadOnlyFinance> financesDisplayed) {
        return String.format(Messages.MESSAGE_FINANCES_LISTED_OVERVIEW, financesDisplayed.size());
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute() {
        throw new UnsupportedOperationException("This method should be implement in child classes");
    }

    //Note: it is better to make the execute() method abstract, by replacing the above method with the line below:
    //public abstract CommandResult execute();

    /**
     * Supplies the data the command will operate on.
     */

    public void setData(AddressBook addressBook,
                        List<? extends ReadOnlyPlayer> relevantPlayers,
                        List<? extends ReadOnlyTeam> relevantTeams,
                        List<? extends ReadOnlyMatch> relevantMatches,
                        List<? extends ReadOnlyFinance> relevantFinances) {
        this.addressBook = addressBook;
        this.relevantPlayers = relevantPlayers;
        this.relevantTeams = relevantTeams;
        this.relevantMatches = relevantMatches;
        this.relevantFinances = relevantFinances;
    }

    /**
     * Extracts the the target player in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */

    protected ReadOnlyPlayer getTargetPlayer() throws IndexOutOfBoundsException {
        return relevantPlayers.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    protected ReadOnlyMatch getTargetMatch() throws IndexOutOfBoundsException {
        return relevantMatches.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    protected ReadOnlyTeam getTargetTeam() throws IndexOutOfBoundsException {
        return relevantTeams.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    protected ReadOnlyFinance getTargetFinance() throws IndexOutOfBoundsException {
        return relevantFinances.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
