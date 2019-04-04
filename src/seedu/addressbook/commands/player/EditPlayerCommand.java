package seedu.addressbook.commands.player;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
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
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.tag.Tag;

/**
 * This Command allows user to edit the player details in the league record
 */

public class EditPlayerCommand extends Command {

    public static final String COMMAND_WORD = "editPlayer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits player profile of the player identified by his/her index number in the league tracker."
            + "\n"
            + "Parameters:\n"
            + "INDEX_OF_PLAYER "
            + "n/NAME "
            + "p/POSITION "
            + "a/AGE "
            + "sal/SALARY "
            + "gs/GOALS_SCORED "
            + "ga/GOALS_ASSISTED \n"
            + "tm/TEAM_NAME "
            + "ctry/NATIONALITY "
            + "jn/JERSEY_NUMBER "
            + "app/APPEARANCE "
            + "hs/ HEALTH_STATUS "
            + "[t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD + " 1 "
            + "sal/" + Salary.EXAMPLE + "\n"
            + "Index of the player can be obtained using the list Command";

    public static final String MESSAGE_SUCCESS = "Player %1$s profile is edited";
    public static final String MESSAGE_NOATTRIBUTE_WARNING = "At least one attribute must be provided for edition";
    private final Name nameItem;
    private final PositionPlayed positionItem;
    private final Age ageItem;
    private final Salary salaryItem;
    private final GoalsScored gsItem;
    private final GoalsAssisted gaItem;
    private final TeamName teamNameItem;
    private final Nationality nationalityItem;
    private final JerseyNumber jnItem;
    private final Appearance appItem;
    private final HealthStatus hsItem;
    private final Set<Tag> tagItem;


    public EditPlayerCommand(int targetIndex,
                             String name, String position, String age, String salary,
                             String goalsScored, String goalsAssisted, String teamName,
                             String nationality, String jerseyNumber, String appearance,
                             String healthStatus, Set<String> tags) throws IllegalValueException {
        super(targetIndex);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.nameItem = (name == null) ? null : new Name(name);
        this.ageItem = (age == null) ? null : new Age(age);
        this.positionItem = (position == null) ? null : new PositionPlayed(position);
        this.salaryItem = (salary == null) ? null : new Salary(salary);
        this.gsItem = (goalsScored == null) ? null : new GoalsScored(goalsScored);
        this.gaItem = (goalsAssisted == null) ? null : new GoalsAssisted(goalsAssisted);
        this.teamNameItem = (teamName == null) ? null : new TeamName(teamName);
        this.nationalityItem = (nationality == null) ? null : new Nationality(nationality);
        this.jnItem = (jerseyNumber == null) ? null : new JerseyNumber(jerseyNumber);
        this.appItem = (appearance == null) ? null : new Appearance(appearance);
        this.hsItem = (healthStatus == null) ? null : new HealthStatus(healthStatus);
        this.tagItem = tagSet;
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPlayer oldPlayer = getTargetPlayer();

            final Player inputPlayer = createInputPlayer(this.nameItem, this.positionItem,
                    this.ageItem, this.salaryItem, this.gsItem, this.gaItem, this.teamNameItem,
                    this.nationalityItem, jnItem, appItem, hsItem, tagItem);
            Player editedPlayer = createEditedPlayer(inputPlayer, oldPlayer);

            addressBook.editPlayer(getTargetPlayer(), editedPlayer);
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedPlayer));
        } catch (UniquePlayerList.PlayerNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE);
        } catch (IndexOutOfBoundsException iobe) {
            return new CommandResult(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX
                    + "\n" + Messages.MESSAGE_NVRLISTBEFOREEDIT);
        }
    }

    /**
     * Create a player object that contains the input information by the user.
     * Some fields may be null.
     *
     * @param nameitem a Name object
     * @param positionItem a PositionPlayer object
     * @param ageItem an Age object
     * @param salaryItem a Salary object
     * @param gsItem a GoalsScored object
     * @param gaItem a GoalsAssisted object
     * @param teamNameItem a TeamName object
     * @param nationalityItem a Nationality object
     * @param jnItem a JerseyNumber Object
     * @param appItem an Appearance object
     * @param hsItem a HealthStatus object
     * @param tagItem a tag Set
     * @return a Player object containing all user-input information
     */
    private static Player createInputPlayer(Name nameitem, PositionPlayed positionItem,
                                            Age ageItem, Salary salaryItem,
                                            GoalsScored gsItem, GoalsAssisted gaItem,
                                            TeamName teamNameItem, Nationality nationalityItem,
                                            JerseyNumber jnItem, Appearance appItem,
                                            HealthStatus hsItem, Set<Tag> tagItem) {

        return new Player(nameitem, positionItem, ageItem, salaryItem, gsItem, gaItem,
                teamNameItem, nationalityItem, jnItem, appItem, hsItem, tagItem);
    }

    /**
     * Creates a Player object containing updated information
     *
     * @param inputPlayer a Player object containing all user input
     * @param oldPlayer the Player object that is to be edited
     * @return an updated Player object containing all updated information with the edition made
     */
    private static Player createEditedPlayer(ReadOnlyPlayer inputPlayer, ReadOnlyPlayer oldPlayer) {
        Name nameItem;
        PositionPlayed positionItem;
        Age ageItem;
        Salary salaryItem;
        GoalsScored gsItem;
        GoalsAssisted gaItem;
        TeamName teamNameItem;
        Nationality nationalityItem;
        JerseyNumber jnItem;
        Appearance appItem;
        HealthStatus hsItem;
        Set<Tag> tagItem;

        if (inputPlayer.getName() == null) {
            nameItem = oldPlayer.getName();
        } else {
            nameItem = inputPlayer.getName();
        }

        if (inputPlayer.getPositionPlayed() == null) {
            positionItem = oldPlayer.getPositionPlayed();
        } else {
            positionItem = inputPlayer.getPositionPlayed();
        }

        if (inputPlayer.getAge() == null) {
            ageItem = oldPlayer.getAge();
        } else {
            ageItem = inputPlayer.getAge();
        }

        if (inputPlayer.getSalary() == null) {
            salaryItem = oldPlayer.getSalary();
        } else {
            salaryItem = inputPlayer.getSalary();
        }

        if (inputPlayer.getGoalsScored() == null) {
            gsItem = oldPlayer.getGoalsScored();
        } else {
            gsItem = inputPlayer.getGoalsScored();
        }

        if (inputPlayer.getGoalsAssisted() == null) {
            gaItem = oldPlayer.getGoalsAssisted();
        } else {
            gaItem = inputPlayer.getGoalsAssisted();
        }

        if (inputPlayer.getTeamName() == null) {
            teamNameItem = oldPlayer.getTeamName();
        } else {
            teamNameItem = inputPlayer.getTeamName();
        }

        if (inputPlayer.getNationality() == null) {
            nationalityItem = oldPlayer.getNationality();
        } else {
            nationalityItem = inputPlayer.getNationality();
        }

        if (inputPlayer.getJerseyNumber() == null) {
            jnItem = oldPlayer.getJerseyNumber();
        } else {
            jnItem = inputPlayer.getJerseyNumber();
        }

        if (inputPlayer.getAppearance() == null) {
            appItem = oldPlayer.getAppearance();
        } else {
            appItem = inputPlayer.getAppearance();
        }

        if (inputPlayer.getHealthStatus() == null) {
            hsItem = oldPlayer.getHealthStatus();
        } else {
            hsItem = inputPlayer.getHealthStatus();
        }

        if (inputPlayer.getTags().isEmpty()) {
            tagItem = oldPlayer.getTags();
        } else {
            tagItem = inputPlayer.getTags();
        }

        return new Player(
                nameItem,
                positionItem,
                ageItem,
                salaryItem,
                gsItem,
                gaItem,
                teamNameItem,
                nationalityItem,
                jnItem,
                appItem,
                hsItem,
                tagItem
        );
    }
}
