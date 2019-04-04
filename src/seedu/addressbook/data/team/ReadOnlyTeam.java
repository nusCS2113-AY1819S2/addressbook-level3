package seedu.addressbook.data.team;

import java.util.Set;

import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.tag.Tag;


/**
 * A read-only immutable interface for a team in the league tracker.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */

public interface ReadOnlyTeam {

    TeamName getTeamName();
    Country getCountry();
    Sponsor getSponsor();
    Set<Player> getPlayers();
    Set<Match> getMatches();
    int getPoints();
    int getWins();
    int getDraws();
    int getLoses();

    /**
     * changes on the returned list will not affect the team's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTeam other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTeamName().equals(this.getTeamName()) // state checks here onwards
                && other.getCountry().equals(this.getCountry())
                && other.getSponsor().equals(this.getSponsor()));
    }

    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName().fullName.trim())
                .append(" | Nationality: ");
        builder.append(getCountry().toString().trim())
                .append(" | Sponsorship: USD ");
        builder.append(getSponsor().toString().trim())
                .append(" |Wins: ");
        builder.append(getWins())
                .append(" |Draw: ");
        builder.append(getDraws())
                .append(" |Lose: ");
        builder.append(getLoses())
                .append(" |Points: ");
        builder.append(getPoints())
                .append(" | Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        builder.append("\nPlayer List:\n");
        for (Player player : getPlayers()) {
            builder.append(player.getName() + "\n");
        }
        builder.append("\nMatch List:\n");
        for (Match match : getMatches()) {
            builder.append(match.getHome() + "vs" + match.getAway());
        }
        return builder.toString();
    }
}
