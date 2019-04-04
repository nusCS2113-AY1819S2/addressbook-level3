package seedu.addressbook.commands.team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.team.ReadOnlyTeam;



/**
 * Finds and lists all teams in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTeam extends Command {

    public static final String COMMAND_WORD = "findteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all teams whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " United";

    private final Set<String> keywords;

    public FindTeam(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyTeam> teamsFound = getTeamsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForTeamListShownSummary(teamsFound), null, teamsFound, null, null);
    }

    /**
     * Retrieve all teams in the address book whose names contain some of the specified keywords.
     */
    private List<ReadOnlyTeam> getTeamsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyTeam> matchedTeams = new ArrayList<>();
        for (ReadOnlyTeam team : addressBook.getAllTeams()) {
            final Set<String> wordsInName = new HashSet<>(team.getTeamName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedTeams.add(team);
            }
        }
        return matchedTeams;
    }
}
