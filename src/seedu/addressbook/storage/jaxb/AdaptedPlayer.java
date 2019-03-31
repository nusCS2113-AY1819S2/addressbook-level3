package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Age;
import seedu.addressbook.data.player.Appearance;
import seedu.addressbook.data.player.GoalsAssisted;
import seedu.addressbook.data.player.GoalsScored;
import seedu.addressbook.data.player.HealthStatus;
import seedu.addressbook.data.player.JerseyNumber;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Nationality;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.PositionPlayed;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.Salary;
import seedu.addressbook.data.player.TeamName;
import seedu.addressbook.data.tag.Tag;

/**
 * JAXB-friendly adapted player data holder class.
 */
public class AdaptedPlayer {

    /**
     * JAXB-friendly place holder for information.
     */
    //    private static class AdaptedPlayerDetail {
    //        @XmlValue
    //        private String value;
    //        @XmlAttribute(required = true)
    //        private boolean isPrivate;
    //    }

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String position;
    @XmlElement(required = true)
    private String age;
    @XmlElement(required = true)
    private String salary;
    @XmlElement(required = true)
    private String goalsScored;
    @XmlElement(required = true)
    private String goalsAssisted;
    @XmlElement(required = true)
    private String teamName;
    @XmlElement(required = true)
    private String nationality;
    @XmlElement(required = true)
    private String jerseyNumber;
    @XmlElement(required = true)
    private String appearance;
    @XmlElement(required = true)
    private String healthStatus;

    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedPlayer() {
    }


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedPlayer
     */
    public AdaptedPlayer(ReadOnlyPlayer source) {
        name = source.getName().fullName;
        position = source.getPositionPlayed().fullPosition;
        age = source.getAge().value;
        salary = source.getSalary().value;
        goalsScored = source.getGoalsScored().value;
        goalsAssisted = source.getGoalsAssisted().value;
        teamName = source.getTeamName().fullTeam;
        nationality = source.getNationality().fullCountry;
        jerseyNumber = source.getJerseyNumber().value;
        appearance = source.getAppearance().value;
        healthStatus = source.getHealthStatus().fullHs;

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
        }
    }

    /**
     * Returns true if any required field is missing.
     * <p>
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
        // second call only happens if phone/email/address are all not null
        return Utils.isAnyNull(name, position, age, salary, goalsScored,
                goalsAssisted, teamName, nationality, jerseyNumber, appearance, healthStatus);
    }

    /**
     * Converts this jaxb-friendly adapted player object into the Player object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted player
     */
    public Player toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final PositionPlayed positionPlayed = new PositionPlayed(this.position);
        final Age age = new Age(this.age);
        final Salary salary = new Salary(this.salary);
        final GoalsScored goalsScored = new GoalsScored(this.goalsScored);
        final GoalsAssisted goalsAssisted = new GoalsAssisted(this.goalsAssisted);
        final TeamName teamName = new TeamName(this.teamName);
        final Nationality nationality = new Nationality(this.nationality);
        final JerseyNumber jerseyNumber = new JerseyNumber(this.jerseyNumber);
        final Appearance appearance = new Appearance(this.appearance);
        final HealthStatus healthStatus = new HealthStatus(this.healthStatus);

        return new Player(name, positionPlayed, age, salary, goalsScored, goalsAssisted,
                teamName, nationality, jerseyNumber, appearance, healthStatus, tags);
    }
}
