package seedu.addressbook.storage.jaxb;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly adapted address book data holder class.
 */
@XmlRootElement(name = "AddressBook")
public class AdaptedAddressBook {

    @XmlElement
    private List<AdaptedPerson> persons = new ArrayList<>();
    @XmlElement
    private List<AdaptedTeam> teams = new ArrayList<>();

    @XmlElement
    private List<AdaptedMatch> matches = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAddressBook() {}

    /**
     * Converts a given AddressBook into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAddressBook
     */
    public AdaptedAddressBook(AddressBook source) {
        persons = new ArrayList<>();
        matches = new ArrayList<>();
        source.getAllPersons().forEach(person -> persons.add(new AdaptedPerson(person)));
        source.getAllMatches().forEach(match -> matches.add(new AdaptedMatch(match)));
        source.getAllTeams().forEach(team -> teams.add(new AdaptedTeam(team)));
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
        return persons.stream().anyMatch(AdaptedPerson::isAnyRequiredFieldMissing)
                || matches.stream().anyMatch(AdaptedMatch::isAnyRequiredFieldMissing)
                || teams.stream().anyMatch(AdaptedTeam::isAnyRequiredFieldMissing);
    }


    /**
     * Converts this jaxb-friendly {@code AdaptedAddressBook} object into the corresponding(@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public AddressBook toModelType() throws IllegalValueException {
        final List<Person> personList = new ArrayList<>();
        final List<Team> teamList = new ArrayList<>();
        final List<Match> matchList = new ArrayList<>();

        for (AdaptedPerson person : persons) {
            personList.add(person.toModelType());
        }

        for (AdaptedTeam team : teams) {
            teamList.add(team.toModelType());
        }
      
        for (AdaptedMatch match : matches) {
            matchList.add(match.toModelType());
        }
        return new AddressBook(
                new UniquePersonList(personList),
                new UniqueTeamList(teamList),
                new UniqueMatchList(matchList));
    }
}
