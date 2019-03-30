package seedu.addressbook.data.player;

import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * A read-only immutable interface for a Player in a football league.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPlayer {

    Name getName();

    PositionPlayed getPositionPlayed();

    Salary getSalary();

    Age getAge();

    GoalsScored getGoalsScored();

    GoalsAssisted getGoalsAssisted();

    Team getTeam();

    Country getCountry();

    JerseyNumber getJerseyNumber();

    Appearance getAppearance();

    HealthStatus getHealthStatus();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the player's internal tags.
     */

    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPlayer other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPositionPlayed().equals(this.getPositionPlayed())
                && other.getAge().equals(this.getAge())
                && other.getSalary().equals(this.getSalary())
                && other.getGoalsScored().equals(this.getGoalsScored())
                && other.getGoalsAssisted().equals(this.getGoalsAssisted())
                && other.getTeam().equals(this.getTeam())
                && other.getCountry().equals(this.getCountry())
                && other.getJerseyNumber().equals(this.getJerseyNumber())
                && other.getAppearance().equals(this.getAppearance())
                && other.getHealthStatus().equals(this.getHealthStatus()));
    }

    /**
     * Formats the player as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();

        builder.append("\n").append("Name: ").append(getName()).append("  |  Position Played: ")
                .append(getPositionPlayed()).append("  |  Age: ").append(getAge()).append("  |  Salary: ")
                .append(getSalary()).append("\n").append("Goals scored: ").append(getGoalsScored())
                .append("  |  Goals assisted: ").append(getGoalsAssisted()).append("  |  Team: ")
                .append(getTeam()).append("  |  Country: ").append(getCountry())
                .append("\n").append("Jersey Number: ").append(getJerseyNumber())
                .append("  |  Appearance: ").append(getAppearance()).append("  |  HealthStatus: ")
                .append(getHealthStatus()).append(" | Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }
}
