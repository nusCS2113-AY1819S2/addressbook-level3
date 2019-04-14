package seedu.addressbook.data.team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.ReadOnlyPlayer;
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
    private final List<Player> playerlist = new ArrayList<>();
    private final List<Match> matchlist = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Team(TeamName teamName,
                Country country,
                Sponsor sponsor,
                List<Match> matchlist,
                List<Player> playerlist,
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
                List<Match> matchlist,
                List<Player> playerlist,
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

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(playerlist);
    }

    @Override
    public List<Match> getMatches() {
        return new ArrayList<>(matchlist);
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

    public void updatePoints() {
        this.points = 3 * this.win + this.draw;
    }

    /**
     * Clearing of match listing will trigger reset of teams result to 0
     */
    public void clearResults() {
        this.win = 0;
        this.lose = 0;
        this.draw = 0;
        this.points = 0;
    }

    public void addPlayer(Player player) {
        this.playerlist.add(player);
    }

    public void removePlayer(ReadOnlyPlayer player) {
        this.playerlist.remove(player);
    }

    public void clearPlayerList() {
        this.playerlist.clear();
    }


    /**
     * Adding of Matches will also initialize the computing for win lose and draw counter
     */

    public void addMatch(Match match) {
        this.matchlist.add(match);
        if (teamName.equals(match.getHome()) && !match.notPlayed()) {
            String result = match.getScore().toString();
            String[] score = result.split("-");
            if (Integer.valueOf(score[0]) > Integer.valueOf(score[1])) {
                this.win++;
            } else if (Integer.valueOf(score[0]) < Integer.valueOf(score[1])) {
                this.lose++;
            } else {
                this.draw++;
            }
        }
        if (teamName.equals(match.getAway()) && !match.notPlayed()) {
            String result = match.getScore().toString();
            String[] score = result.split("-");
            if (Integer.valueOf(score[1]) > Integer.valueOf(score[0])) {
                this.win++;
            } else if (Integer.valueOf(score[1]) < Integer.valueOf(score[0])) {
                this.lose++;
            } else {
                this.draw++;
            }
        }
    }

    /**
     * Removal of matches will result in corresponding changes in Team Score
     * @param match
     */

    public void removeMatch(ReadOnlyMatch match) {
        this.matchlist.remove(match);
        if (teamName.equals(match.getHome()) && !match.notPlayed()) {
            String result = match.getScore().toString();
            String[] score = result.split("-");
            if (Integer.valueOf(score[0]) > Integer.valueOf(score[1])) {
                this.win--;
            } else if (Integer.valueOf(score[0]) < Integer.valueOf(score[1])) {
                this.lose--;
            } else {
                this.draw--;
            }
        }
        if (teamName.equals(match.getAway()) && !match.notPlayed()) {
            String result = match.getScore().toString();
            String[] score = result.split("-");
            if (Integer.valueOf(score[1]) > Integer.valueOf(score[0])) {
                this.win--;
            } else if (Integer.valueOf(score[1]) < Integer.valueOf(score[0])) {
                this.lose--;
            } else {
                this.draw--;
            }
        }
    }

    /**
     * Clears match list in teams.
     * @throws IllegalValueException
     */
    public void clearMatchList() throws IllegalValueException {
        this.matchlist.clear();
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
        return getAsTextShowSome();
    }

}

