package seedu.addressbook.ui;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * Used for formatting text for display. e.g. for adding text decorations.
 */
public class Formatter {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = " ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();


    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    private static final int DISPLAYED_INDEX_OFFSET = 1;


    /** Formats the given strings for displaying to the user. */
    public String format(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String m : messages) {
            sb.append(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX) + LS);
        }
        return sb.toString();
    }

    /** Formats the given list of players for displaying to the user. */
    public String formatPersonResult(List<? extends ReadOnlyPlayer> players) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPlayer player : players) {
            formattedPersons.add(player.getAsTextShowAll());
        }
        return format(asIndexedList(formattedPersons));
    }

    /** Formats the given list of teams for displaying to the user. */
    public String formatTeamResult(List<? extends ReadOnlyTeam> teams) {
        final List<String> formattedTeams = new ArrayList<>();
        for (ReadOnlyTeam team : teams) {
            formattedTeams.add(team.getAsTextShowSome());
        }
        return format(asIndexedList(formattedTeams));
    }

    /** Formats the given list of matches for displaying to the user. */
    public String formatMatchResult(List<? extends ReadOnlyMatch> matches) {
        final List<String> formattedMatches = new ArrayList<>();
        for (ReadOnlyMatch match : matches) {
            formattedMatches.add(match.getAsTextShowAll());
        }
        return format(asIndexedList(formattedMatches));
    }

    /** Formats the given list of finances for displaying to the user. */
    public String formatFinanceResult(List<? extends ReadOnlyFinance> finances) {
        final List<String> formattedFinances = new ArrayList<>();
        for (ReadOnlyFinance finance : finances) {
            formattedFinances.add(finance.getAsTextShowSome());
        }
        return format(asIndexedList(formattedFinances));
    }

    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }
}
