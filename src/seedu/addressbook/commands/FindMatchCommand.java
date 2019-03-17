package seedu.addressbook.commands;

import seedu.addressbook.data.match.ReadOnlyMatch;

import java.util.*;

/**
 * Finds and lists all matches in address book with date that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMatchCommand extends Command {

    public static final String COMMAND_WORD = "findmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all matches with date that contains any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " MAR APR JUN";

    private final Set<String> keywords;

    public FindMatchCommand(Set<String> keywords) {
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
        final List<ReadOnlyMatch> matchesFound = getMatchesWithDateContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForMatchListShownSummary(matchesFound), null, matchesFound);
    }

    /**
     * Retrieve all matches in the address book with date containing some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of matches found
     */
    private List<ReadOnlyMatch> getMatchesWithDateContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyMatch> matchedMatches = new ArrayList<>();
        for (ReadOnlyMatch match : addressBook.getAllMatches()) {
            final Set<String> wordsInDate = new HashSet<>(match.getDate().getWordsInDate());
            if (!Collections.disjoint(wordsInDate, keywords)) {
                matchedMatches.add(match);
            }
        }
        return matchedMatches;
    }

}