package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.team.ReadOnlyTeam;

import java.util.List;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public abstract class Command {
    protected AddressBook addressBook;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    protected List<? extends ReadOnlyMatch> relevantMatches;
    protected List<? extends ReadOnlyTeam> relevantTeams;

    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
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

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute(){
        throw new UnsupportedOperationException("This method should be implement in child classes");
    }

    //Note: it is better to make the execute() method abstract, by replacing the above method with the line below:
    //public abstract CommandResult execute();

    /**
     * Supplies the data the command will operate on.
     */

    public void setData(AddressBook addressBook,
                        List<? extends ReadOnlyPerson> relevantPersons,
                        List<? extends ReadOnlyTeam> relevantTeams,
                        List<? extends ReadOnlyMatch> relevantMatches) {
        this.addressBook = addressBook;
        this.relevantPersons = relevantPersons;
        this.relevantTeams = relevantTeams;
        this.relevantMatches = relevantMatches;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    protected ReadOnlyMatch getTargetMatch() throws IndexOutOfBoundsException {
        return relevantMatches.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    protected ReadOnlyTeam getTargetTeam() throws IndexOutOfBoundsException {
        return relevantTeams.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
