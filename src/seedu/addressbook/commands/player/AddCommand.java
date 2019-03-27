package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.*;
import seedu.addressbook.data.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a player to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addPlayer";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ":\n" + "Adds a player to the League Tracker. "+ "\n"
            + "Parameters:\n" +
            " NAME p/POSITION \n" +
            " a/AGE \n" +
            " sal/SALARY \n" +
            "gs/GOALS_SCORED \n" +
            "ga/GOALS_ASSISTED \n" +
            "tm/TEAM \n" +
            "ctry/COUNTRY \n" +
            "jn/JERSEY_NUMBER \n" +
            "app/APPEARANCE \n" +
            "hs/ HEALTH_STATUS \n" +
            "[t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " Lionel Messi p/RW a/31 sal/20000000 gs/30 ga/25 tm/FC_BARCELONA ctry/Argentina jn/10" +
                    " app/40 hs/HEALTHY t/friends t/GREATEST_OF_ALL_TIME";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAYER = "This player already exists in the address book";

    private final Player toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String name, String position,String age,String salary, String goalsScored, String goalsAssisted,
                      String team, String country, String jerseyNumber,
                      String appearance, String healthStatus,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Player(
                new Name(name),
                new PositionPlayed(position),
                new Age(age),
                new Salary(salary),
                new GoalsScored(goalsScored),
                new GoalsAssisted(goalsAssisted),
                new Team(team),
                new Country(country),
                new JerseyNumber(jerseyNumber),
                new Appearance(appearance),
                new HealthStatus(healthStatus),
                tagSet
        );
    }

    public AddCommand(Player toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyPlayer getPlayer() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addPlayer(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePlayerList.DuplicatePlayerException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PLAYER);
        }
    }

}
