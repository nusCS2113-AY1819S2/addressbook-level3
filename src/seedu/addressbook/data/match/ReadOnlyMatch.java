package seedu.addressbook.data.match;

import java.util.Set;

import seedu.addressbook.data.player.Name;

/**
 * A read-only immutable interface for a match in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMatch {

    Date getDate();

    Home getHome();

    Away getAway();

    TicketSales getHomeSales();

    TicketSales getAwaySales();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the match's internal tags.
     */
    Set<Name> getGoalScorers();

    Set<Name> getOwnGoalScorers();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMatch other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDate().equals(this.getDate()) // state checks here onwards
                && other.getHome().equals(this.getHome())
                && other.getAway().equals(this.getAway())
                && other.getHomeSales().equals(this.getHomeSales())
                && other.getAwaySales().equals(this.getAwaySales()));
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
        builder.append(getAway());
        if (!getHomeSales().value.isEmpty()) {
            builder.append(" Home Sales: ")
                    .append(getHomeSales());
            builder.append(" Away Sales: ")
                    .append(getAwaySales());
            builder.append(" Goal Scorers: ");
            if (getGoalScorers().isEmpty()) {
                builder.append("none");
            }
            for (Name goalScorer : getGoalScorers()) {
                builder.append(goalScorer);
            }
            builder.append(" Own Goals: ");
            if (getOwnGoalScorers().isEmpty()) {
                builder.append("none");
            }
            for (Name ownGoalScorer : getOwnGoalScorers()) {
                builder.append(ownGoalScorer);
            }
        } else {
            builder.append(" Not Played");
        }
        return builder.toString();
    }
}
