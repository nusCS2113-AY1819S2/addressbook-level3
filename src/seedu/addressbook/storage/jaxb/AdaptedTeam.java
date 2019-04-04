package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.team.Country;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.TeamName;

/**
 * JAXB-friendly adapted team data holder class.
 */
public class AdaptedTeam {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String country;
    @XmlElement(required = true)
    private String sponsor;
    @XmlElement(required = true)
    private String win;
    @XmlElement(required = true)
    private String lose;
    @XmlElement(required = true)
    private String draw;
    @XmlElement(required = true)
    private String points;
    @XmlElement
    private List<AdaptedMatch> matchlist = new ArrayList<>();
    @XmlElement
    private List<AdaptedPlayer> playerlist = new ArrayList<>();
    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedTeam() {}


    /**
     * Converts a given team into this class for JAXB use.
     */
    public AdaptedTeam(ReadOnlyTeam source) {
        name = source.getTeamName().fullName;
        country = source.getCountry().toString();
        sponsor = source.getSponsor().toString();
        win = Integer.toString(source.getWins());
        lose = Integer.toString(source.getLoses());
        draw = Integer.toString(source.getDraws());
        points = Integer.toString(source.getPoints());
        matchlist = new ArrayList<>();
        for (Match match : source.getMatches()) {
            matchlist.add(new AdaptedMatch((match)));
        }

        playerlist = new ArrayList<>();
        for (Player player : source.getPlayers()) {
            playerlist.add(new AdaptedPlayer(player));
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
        }
    }

    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        return Utils.isAnyNull(name, country);
    }

    /**
     * Converts this jaxb-friendly adapted team object into the team object.
     */
    public Team toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        final Set<Player> players = new HashSet<>();
        final Set<Match> matches = new HashSet<>();
        for (AdaptedMatch match : matchlist) {
            matches.add(match.toModelType());
        }
        for (AdaptedPlayer player : playerlist) {
            players.add(player.toModelType());
        }
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final TeamName teamName = new TeamName(this.name);
        final Country country = new Country(this.country);
        final Sponsor sponsor = new Sponsor(this.sponsor);
        final int win = Integer.valueOf(this.win);
        final int lose = Integer.valueOf(this.lose);
        final int draw = Integer.valueOf(this.draw);
        final int points = Integer.valueOf(this.points);
        return new Team(teamName, country, sponsor, win, lose, draw, points, matches, players, tags);
    }
}
