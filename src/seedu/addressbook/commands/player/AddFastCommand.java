package seedu.addressbook.commands.player;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Age;
import seedu.addressbook.data.player.Nationality;
import seedu.addressbook.data.player.JerseyNumber;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.PositionPlayed;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.Salary;
import seedu.addressbook.data.player.TeamName;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.tag.Tag;

/**
 * Adds a player to the address book.
 * With some fields set as default to make adding more user friendly
 */
public class AddFastCommand extends Command {

    public static final String COMMAND_WORD = "addFast";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ":\n" + "Adds a player to the League Tracker. " + "\n"
                    + "Parameters:\n"
                    + "NAME p/POSITION \n"
                    + "a/AGE \n"
                    + "sal/SALARY \n"
                    + "tm/TEAM_NAME \n"
                    + "ctry/NATIONALITY \n"
                    + "jn/JERSEY_NUMBER \n"
                    + "[t/TAG]...\n\t"
                    + "Example: "
                    + COMMAND_WORD
                    + " Lionel Messi p/RW a/31 sal/20000000 tm/FC_BARCELONA ctry/Argentina jn/10"
                    + " t/friends t/GREATEST_OF_ALL_TIME";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAYER = "This player already exists in the address book";

    private final Player toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddFastCommand(String name, String position, String age, String salary,
                          String teamName, String nationality, String jerseyNumber,
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
                new TeamName(teamName),
                new Nationality(nationality),
                new JerseyNumber(jerseyNumber),
                tagSet
        );
    }

    public AddFastCommand(Player toAdd) {
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
