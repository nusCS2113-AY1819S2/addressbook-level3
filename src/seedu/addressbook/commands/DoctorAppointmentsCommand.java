//@@author matthiaslum
package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DoctorAppointmentsCommand extends Command {

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM d kk mm");
    public static final String COMMAND_WORD = "DoctorAppointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all patients who have appointments with a certain doctor, and sorts them chronologically";
//            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
//            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
//            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public DoctorAppointmentsCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        Indicator.setLastCommand("DoctorAppointments");
        return new CommandResult(getMessageForAppointmentsShownSummary(personsFound, keywords.toString()), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getDoctor().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                LocalDateTime date = LocalDateTime.parse(person.getAppointment().toString(), formatter);
                person.setLocalDateTime(date);
                matchedPersons.add(person);
            }
        }
        Collections.sort(matchedPersons, new SortDate());
        return matchedPersons;
    }

}
//@@author