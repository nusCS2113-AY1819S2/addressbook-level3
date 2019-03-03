package seedu.addressbook.data.schedule;

import java.util.Calendar;

/**
 * Represents a Match in the league book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Match {

    private TeamName home;
    private TeamName away;
    private Calendar date;
    private Outcome outcome;

    /**
     * Assumption: Every field must be present and not null.
     */
    public Match(TeamName home, TeamName away, Calendar date, Outcome outcome) {
        this.home = home;
        this.away = away;
        this.date = date;
        this.outcome = outcome;
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
    public Calendar getDate() {
        return date;
    }

    @Override
    public Outcome getOutcome() {
        return outcome;
    }
}
