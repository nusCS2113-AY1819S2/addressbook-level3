package seedu.addressbook.data.match;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.player.Name;

/**
 * Represents a match in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Match implements ReadOnlyMatch {

    private Date date;
    private Home home;
    private Away away;
    private TicketSales homeSales;
    private TicketSales awaySales;

    private final Set<Name> goalScorers = new HashSet<>();
    private final Set<Name> ownGoalScorers = new HashSet<>();

    /**
     * Assumption: Every field must be present and not null.
     */
    public Match(Date date, Home home, Away away, TicketSales homeSales, TicketSales awaySales,
                 Set<Name> goalScorers, Set<Name> ownGoalScorers) {
        this.date = date;
        this.home = home;
        this.away = away;
        this.homeSales = homeSales;
        this.awaySales = awaySales;
        this.goalScorers.addAll(goalScorers);
        this.ownGoalScorers.addAll(ownGoalScorers);
    }

    /**
     * Copy constructor.
     */
    public Match(ReadOnlyMatch source) {
        this(source.getDate(), source.getHome(), source.getAway(), source.getHomeSales(),
                source.getAwaySales(), source.getGoalScorers(), source.getOwnGoalScorers());
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Home getHome() {
        return home;
    }

    @Override
    public Away getAway() {
        return away;
    }

    @Override
    public TicketSales getHomeSales() {
        return homeSales;
    }

    @Override
    public TicketSales getAwaySales() {
        return awaySales;
    }

    @Override
    public Set<Name> getGoalScorers() {
        return new HashSet<>(goalScorers);
    }

    @Override
    public Set<Name> getOwnGoalScorers() {
        return new HashSet<>(ownGoalScorers);
    }

    /**
     * Replaces this match's goalScorers with the goalScorers in {@code replacement}.
     */
    public void setGoalScorers(Set<Name> replacement) {
        goalScorers.clear();
        goalScorers.addAll(replacement);
    }

    public void setOwnGoalScorers(Set<Name> replacement) {
        ownGoalScorers.clear();
        ownGoalScorers.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMatch // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMatch) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, home, away, homeSales, awaySales,
                goalScorers, ownGoalScorers);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }
}
