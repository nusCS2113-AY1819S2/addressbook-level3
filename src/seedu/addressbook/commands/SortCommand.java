package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;

//@@author WuPeiHsuan

/**
 * Lists all persons in the address book in sorted order to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all persons in the address book as a list sorted in alphabetical order with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;

    public final String attribute;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM d kk mm");

    public SortCommand(String attribute) {
        this.attribute = attribute;
    }


    @Override
    public CommandResult execute() {

        final List<ReadOnlyPerson> personsSortedByStatus = getPersonsSortedByStatus();

        final List<ReadOnlyPerson> personsSortedByDate = getPersonsSortedByDate();


        addressBook.sorted(attribute);
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();

        if(attribute.equals("status")) {
            return new CommandResult(getMessageForPersonListShownSummary(personsSortedByStatus), personsSortedByStatus );
        }else if(attribute.equals("appointment")){
            return new CommandResult(getMessageForPersonListShownSummary(personsSortedByDate), personsSortedByDate);
        }
        return new CommandResult(getMessageForPersonSortShownSummary(allPersons, attribute), allPersons);

    }

    private List<ReadOnlyPerson> getPersonsSortedByStatus() {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Critical")) {
                matchedPersons.add(person);
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Waiting for Surgery")) {
                matchedPersons.add(person);
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Life-support")) {
                matchedPersons.add(person);
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Waiting for doctor appointment")) {
                matchedPersons.add(person);
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Therapy")) {
                matchedPersons.add(person);
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String getStatus = person.getStatus().toString();
            if (getStatus.equals("Observation")) {
                matchedPersons.add(person);
            }
        }

        return matchedPersons;
    }

    private List<ReadOnlyPerson> getPersonsSortedByDate() {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
                LocalDateTime date = LocalDateTime.parse(person.getAppointment().toString(), formatter);
                person.setLocalDateTime(date);
                matchedPersons.add(person);
        }
        Collections.sort(matchedPersons, new SortDate());
        return matchedPersons;
    }
}
//@@author