package seedu.addressbook.commands.team;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.team.Country;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.TeamName;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * Adds a team to the address book.
 */

public class AddTeam extends Command {

    public static final String COMMAND_WORD = "addteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a team to the address book. "
            + "Parameters: NAME c/COUNTRY s/SPONSORSHIP BALANCE [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " Singapore United c/Singapore s/5487 t/Lousy";

    public static final String MESSAGE_SUCCESS = "New team added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the address book";

    private final Team toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddTeam(String name,
                   String country,
                   String sponsor,
                   Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Team(
                new TeamName(name),
                new Country(country),
                new Sponsor(sponsor),
                new HashSet<>(),
                new HashSet<>(),
                tagSet
        );
    }

    public AddTeam(Team toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyTeam getTeam() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addTeam(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTeamList.DuplicateTeamException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TEAM);
        }
    }
}
