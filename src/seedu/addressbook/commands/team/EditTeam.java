package seedu.addressbook.commands.team;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.team.Country;
import seedu.addressbook.data.team.EditTeamDescriptor;
import seedu.addressbook.data.team.Name;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;



/**
 * Edits a team to the address book.
 */

public class EditTeam extends Command {

    public static final String COMMAND_WORD = "editTeam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits the details of the team identified by the index number used in the displayed team list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "(listTeam must be used before this command to retrieve index for team to be deleted)\n\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[n/NAME]"
            + "[c/COUNTRY] "
            + "[s/SPONSOR] "
            + "[t/TAGS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "c/" + Country.EXAMPLE;

    public static final String MESSAGE_EDIT_TEAM_SUCCESS = "Edited team: %1$s";
    public static final String MESSAGE_NOARGS = "At least one field to edit must be provided.\n%1$s";

    private final EditTeamDescriptor editTeamDescriptor;

    public EditTeam(int targetVisibleIndex,
                    String name,
                    String country,
                    String sponsor,
                    Set<String> tags) throws IllegalValueException {
        super(targetVisibleIndex);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.editTeamDescriptor = new EditTeamDescriptor(name, country, sponsor, tagSet);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTeam teamToEdit = getTargetTeam();
            Team editedTeam = createEditedTeam(teamToEdit, editTeamDescriptor);

            addressBook.editTeam(teamToEdit, editedTeam);

            return new CommandResult(String.format(MESSAGE_EDIT_TEAM_SUCCESS, editedTeam));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        } catch (UniqueTeamList.TeamNotFoundException tnfe) {
            return new CommandResult(Messages.MESSAGE_TEAM_NOT_IN_LEAGUE_TRACKER);
        }

    }

    /**
     * Creates and returns a team with the details of teamToEdit edited with editTeamDescriptor.
     */
    private static Team createEditedTeam(ReadOnlyTeam teamToEdit,
                                         EditTeamDescriptor editTeamDescriptor) {

        Name updatedName = checkName(editTeamDescriptor.getName(), teamToEdit.getName());
        Country updatedCountry = checkCountry(editTeamDescriptor.getCountry(), teamToEdit.getCountry());
        Sponsor updatedSponsor = checkSponsor(editTeamDescriptor.getSponsor(), teamToEdit.getSponsor());
        Set<Tag> updatedTagset = checkTagset(editTeamDescriptor.getTags(), teamToEdit.getTags());

        return new Team(updatedName, updatedCountry, updatedSponsor, teamToEdit.getPlayers(), updatedTagset);
    }

    /**
     * Check for new name value.
     */
    private static Name checkName(Name newEdit, Name oldInfo) {
        if (newEdit == null) {
            return oldInfo;
        }
        return newEdit;
    }

    /**
     * Check for new Nationality value.
     */
    private static Country checkCountry(Country newEdit, Country oldInfo) {
        if (newEdit == null) {
            return oldInfo;
        }
        return newEdit;
    }


    /**
     * Check for new Sponsor value.
     */
    private static Sponsor checkSponsor(Sponsor newEdit, Sponsor oldInfo) {
        if (newEdit == null) {
            return oldInfo;
        }
        return newEdit;
    }

    /**
     * Check for new address value.
     */
    private static Set<Tag> checkTagset(Set<Tag> newEdit, Set<Tag> oldInfo) {
        if (newEdit.isEmpty()) {
            return oldInfo;
        }
        return newEdit;
    }

}
