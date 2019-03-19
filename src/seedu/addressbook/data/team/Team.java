package seedu.addressbook.data.team;


import seedu.addressbook.data.player.Person;
import seedu.addressbook.data.tag.Tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a team in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Team implements ReadOnlyTeam {

    private Name name;
    private Country country;
    private Sponsor sponsor;
    private final Set<Person> playerlist = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Team(Name name, Country country,Sponsor sponsor ,Set<Person> playerlist, Set<Tag> tags) {
        this.name = name;
        this.country = country;
        this.sponsor = sponsor;
        this.playerlist.addAll(playerlist);
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Team(ReadOnlyTeam source) {
        this(source.getName(), source.getCountry(),source.getSponsor(),source.getPlayers(), source.getTags());
    }

    @Override
    public Set<Person> getPlayers() {
        return new HashSet<>(playerlist);
    }

    @Override
    public Sponsor getSponsor() {
        return sponsor;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Replaces this team's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    public void setPlayers(Set<Person> replacement) {
        playerlist.clear();
        playerlist.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTeam // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTeam) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, country, sponsor, playerlist ,tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}

