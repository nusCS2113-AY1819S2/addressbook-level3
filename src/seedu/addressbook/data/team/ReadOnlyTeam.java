package seedu.addressbook.data.team;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.tag.Tag;

import java.util.List;
import java.util.Set;

/**
 * A read-only immutable interface for a Team in the league tracker.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */

public interface ReadOnlyTeam {

    Name getName();
    Country getCountry();
    Sponsor getSponsor();
    Set<Person> getPlayers();

    /**
     * changes on the returned list will not affect the team's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTeam other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getCountry().equals(this.getCountry())
                && other.getSponsor().equals(this.getSponsor()));
    }

    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName().fullName.trim())
                .append(" | Country: ");
        builder.append(getCountry().toString().trim())
                .append(" | Sponsorship: USD ");
        builder.append(getSponsor().toString().trim())
                .append(" | Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

}
