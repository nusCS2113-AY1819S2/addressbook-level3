package seedu.addressbook.data.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.team.TeamName;

/**
 * Represents a match in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Match implements ReadOnlyMatch {

    private MatchDate date;
    private TeamName home;
    private TeamName away;
    private TicketSales homeSales;
    private TicketSales awaySales;
    private Score score;

    private final List<Name> goalScorers = new ArrayList<>();
    private final List<Name> ownGoalScorers = new ArrayList<>();

    /**
     * Assumption: Every field must be present and not null.
     */
    public Match(MatchDate date, TeamName home, TeamName away, TicketSales homeSales, TicketSales awaySales,
                 List<Name> goalScorers, List<Name> ownGoalScorers, Score score) {
        this.date = date;
        this.home = home;
        this.away = away;
        this.homeSales = homeSales;
        this.awaySales = awaySales;
        this.goalScorers.addAll(goalScorers);
        this.ownGoalScorers.addAll(ownGoalScorers);
        this.score = score;
    }

    /**
     * Copy constructor.
     */
    public Match(ReadOnlyMatch source) {
        this(source.getDate(), source.getHome(), source.getAway(), source.getHomeSales(),
                source.getAwaySales(), source.getGoalScorers(), source.getOwnGoalScorers(), source.getScore());
    }

    @Override
    public MatchDate getDate() {
        return date;
    }

    @Override
    public TeamName getHome() {
        return home;
    }

    @Override
    public TeamName getAway() {
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
    public List<Name> getGoalScorers() {
        return new ArrayList<>(goalScorers);
    }

    @Override
    public List<Name> getOwnGoalScorers() {
        return new ArrayList<>(ownGoalScorers);
    }

    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Replaces this match's goalScorers with the goalScorers in {@code replacement}.
     */
    public void setGoalScorers(List<Name> replacement) {
        goalScorers.clear();
        goalScorers.addAll(replacement);
    }

    /**
     * Replaces this match's ownGoalScorers with the goalScorers in {@code replacement}.
     */
    public void setOwnGoalScorers(List<Name> replacement) {
        ownGoalScorers.clear();
        ownGoalScorers.addAll(replacement);
    }

    /**
     * Replaces this match's score with the goalScorers in {@code replacement}.
     */
    public void setScore(String score) {
        this.score = new Score(score);
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
        return getAsTextShowSome();
    }
}
