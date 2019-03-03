package seedu.addressbook.data.schedule;

import java.util.GregorianCalendar;

/**
 * Represents a Match in the League Tracker.
 */
public class Match {
    private GregorianCalendar date;
    private String home;
    private String away;

    public Match(GregorianCalendar date, String home, String away){
        this.date = date;
        this.home = home;
        this.away = away;
    }

    public GregorianCalendar getDate{
        return date;
    }

    public String getHome{
        return home;
    }

    public String getAway{
        return away;
    }
}
