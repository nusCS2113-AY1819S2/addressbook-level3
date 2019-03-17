package seedu.addressbook.data.match;

import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * A read-only immutable interface for a Match in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMatch {

    Date getDate();

    Home getHome();

    Away getAway();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the match's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMatch other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDate().equals(this.getDate()) // state checks here onwards
                && other.getHome().equals(this.getHome())
                && other.getAway().equals(this.getAway()));
    }

    /**
     * Formats the match as text, showing all match details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
                .append(" Home: ");
        builder.append(getHome())
                .append(" Away: ");
        builder.append(getAway())
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }
}
