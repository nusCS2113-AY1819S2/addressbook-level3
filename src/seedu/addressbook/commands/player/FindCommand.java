package seedu.addressbook.commands.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.player.ReadOnlyPlayer;

/**
 * Finds and lists all players in the league whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all players whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " Messi Ronaldo";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
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
        final List<ReadOnlyPlayer> playersFound = getPlayersWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPlayerListShownSummary(playersFound), playersFound, null, null, null);
    }

    /**
     * Retrieve all players in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of players found
     */

    private List<ReadOnlyPlayer> getPlayersWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPlayer> matchedPlayers = new ArrayList<>();
        for (ReadOnlyPlayer player : addressBook.getAllPlayers()) {
            final Set<String> wordsInName = new HashSet<>(player.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPlayers.add(player);
            }
        }
        return matchedPlayers;
    }

}
