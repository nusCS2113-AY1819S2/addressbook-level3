package seedu.addressbook.data.match;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * Represents a Match in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Match implements ReadOnlyMatch {

    private Date date;
    private Home home;
    private Away away;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Assumption: Every field must be present and not null.
     */
    public Match(Date date, Home home, Away away, Set<Tag> tags) {
        this.date = date;
        this.home = home;
        this.away = away;
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Match(ReadOnlyMatch source) {
        this(source.getDate(), source.getHome(), source.getAway(), source.getTags());
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
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Replaces this match's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
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
        return Objects.hash(date, home, away, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }
}