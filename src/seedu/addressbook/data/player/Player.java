package seedu.addressbook.data.player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.team.TeamName;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Player implements ReadOnlyPlayer {

    private Name name;
    private PositionPlayed positionPlayed;
    private Salary salary;
    private Age age;
    private GoalsScored goalsScored;
    private GoalsAssisted goalsAssisted;
    private TeamName teamName;
    private Nationality nationality;
    private JerseyNumber jerseyNumber;
    private Appearance appearance;
    private HealthStatus healthStatus;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Assumption: Every field must be present and not null.
     */

    public Player(Name name, PositionPlayed positionPlayed, Age age, Salary salary, GoalsScored goalsScored,
                  GoalsAssisted goalsAssisted, TeamName teamName, Nationality nationality, JerseyNumber jerseyNumber,
                  Appearance appearance, HealthStatus healthStatus, Set<Tag> tags) {
        this.name = name;
        this.positionPlayed = positionPlayed;
        this.age = age;
        this.salary = salary;
        this.goalsScored = goalsScored;
        this.goalsAssisted = goalsAssisted;
        this.teamName = teamName;
        this.nationality = nationality;
        this.jerseyNumber = jerseyNumber;
        this.appearance = appearance;
        this.healthStatus = healthStatus;

        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Player(ReadOnlyPlayer source) {
        this(source.getName(), source.getPositionPlayed(), source.getAge(), source.getSalary(),
                source.getGoalsScored(), source.getGoalsAssisted(), source.getTeamName(),
                source.getNationality(), source.getJerseyNumber(), source.getAppearance(),
                source.getHealthStatus(), source.getTags());
    }

    /**
     * User Default Constructor
     * allow user to create a player with some attributes as default value
     */

    public Player(Name name, PositionPlayed positionPlayed, Age age, Salary salary, TeamName teamName,
                  Nationality nationality, JerseyNumber jerseyNumber, Set<Tag> tags) throws IllegalValueException {
        this(name, positionPlayed, age, salary, new GoalsScored("0"),
                new GoalsAssisted("0"), teamName, nationality, jerseyNumber, new Appearance("0"),
                new HealthStatus("Healthy"), tags);
    }


    @Override
    public Name getName() {
        return name;
    }

    @Override
    public PositionPlayed getPositionPlayed() {
        return positionPlayed;
    }

    @Override
    public Salary getSalary() {
        return salary;
    }

    @Override
    public Age getAge() {
        return age;
    }

    @Override
    public GoalsScored getGoalsScored() {
        return goalsScored;
    }

    @Override
    public GoalsAssisted getGoalsAssisted() {
        return goalsAssisted;
    }

    @Override
    public TeamName getTeamName() {
        return teamName;
    }

    @Override
    public Nationality getNationality() {
        return nationality;
    }

    @Override
    public JerseyNumber getJerseyNumber() {
        return jerseyNumber;
    }

    @Override
    public Appearance getAppearance() {
        return appearance;
    }

    @Override
    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Replaces this player's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPlayer // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPlayer) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, positionPlayed, age, salary, goalsScored,
                goalsAssisted, teamName, nationality, jerseyNumber, appearance, healthStatus, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}
