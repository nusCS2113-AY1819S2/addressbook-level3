package seedu.addressbook.data.match;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Represents a match date in the address book.
 */
public class MatchDate implements Comparable<MatchDate> {

    public static final String MESSAGE_INVALID_DATE_FORMAT = "Date should be valid and in format: dd mmm yyyy";
    public static final String EXAMPLE = "13 Jan 1991";
    public final Calendar calendar = new GregorianCalendar();

    /**
     * Constructs given MatchDate
     */
    public MatchDate(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        df.setLenient(false);
        Date date = df.parse(dateString);
        this.calendar.setTime(date);
    }

    /**
     * Retrieves a listing of every word in the Date, in order.
     */
    public List<String> getWordsInDate() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return Arrays.asList(df.format(calendar.getTime()).split("\\s+"));
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(calendar.getTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchDate // instanceof handles nulls
                && this.calendar.equals(((MatchDate) other).calendar)); // state check
    }

    @Override
    public int hashCode() {
        return calendar.hashCode();
    }

    @Override
    public int compareTo(MatchDate date) {
        return this.calendar.compareTo(date.calendar);
    }
}
