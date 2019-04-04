package seedu.addressbook.data.team;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.tag.Tag;


/**
 * Represents a team in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Team implements ReadOnlyTeam {

    private TeamName teamName;
    private Country country;
    private Sponsor sponsor;
    private int win;
    private int lose;
    private int draw;
    private int points;
    private final Set<Player> playerlist = new HashSet<>();
    private final Set<Match> matchlist = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Team(TeamName teamName,
                Country country,
                Sponsor sponsor,
                Set<Match> matchlist,
                Set<Player> playerlist,
                Set<Tag> tags) {
        this.teamName = teamName;
        this.country = country;
        this.sponsor = sponsor;
        this.win = 0;
        this.lose = 0;
        this.draw = 0;
        this.points = 0;
        this.matchlist.addAll(matchlist);
        this.playerlist.addAll(playerlist);
        this.tags.addAll(tags);
    }

    public Team(TeamName teamName,
                Country country,
                Sponsor sponsor,
                int win,
                int lose,
                int draw,
                int points,
                Set<Match> matchlist,
                Set<Player> playerlist,
                Set<Tag> tags) {
        this.teamName = teamName;
        this.country = country;
        this.sponsor = sponsor;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.points = points;
        this.matchlist.addAll(matchlist);
        this.playerlist.addAll(playerlist);
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Team(ReadOnlyTeam source) {
        this(source.getTeamName(),
                source.getCountry(),
                source.getSponsor(),
                source.getMatches(),
                source.getPlayers(),
                source.getTags());
    }

    @Override
    public Set<Player> getPlayers() {
        return new HashSet<>(playerlist);
    }

    @Override
    public Set<Match> getMatches() {
        return new HashSet<>(matchlist);
    }

    @Override
    public Sponsor getSponsor() {
        return sponsor;
    }

    @Override
    public TeamName getTeamName() {
        return teamName;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public int getWins() {
        return win;
    }

    @Override
    public int getLoses() {
        return lose;
    }

    @Override
    public int getDraws() {
        return draw;
    }

    /**
     * Replaces this team's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    public void setPlayers(Set<Player> replacement) {
        playerlist.clear();
        playerlist.addAll(replacement);
    }

    public void updatePoints(int win, int draw) {
        this.points = (win * 3) + draw;
    }

    public void addplayer(Player player) {
        this.playerlist.add(player);
    }

    public void addmatch(Match match) {
        this.matchlist.add(match);
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
        return Objects.hash(teamName, country, sponsor, playerlist, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}

