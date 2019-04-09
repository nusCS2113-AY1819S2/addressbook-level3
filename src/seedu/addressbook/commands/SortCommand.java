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
            + "Displays all persons in the address book as a list sorted by desired column with index numbers.\n\t"
            + "Parameters: name / appointment / status\n\t"
            + "Example: " + COMMAND_WORD + " name\n";

    public final String attribute;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM d kk mm");

    public SortCommand(String attribute) {
        this.attribute = attribute;
    }


    @Override
    public CommandResult execute() {


        addressBook.sorted(attribute);
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();

        if(attribute.equals("status")) {
            final List<ReadOnlyPerson> personsSortedByStatus = getPersonsSortedByStatus();
            return new CommandResult(getMessageForPersonSortShownSummary(personsSortedByStatus, attribute), personsSortedByStatus );

        }else if(attribute.equals("appointment")){
            final List<ReadOnlyPerson> personsSortedByDate = getPersonsSortedByDate();
            return new CommandResult(getMessageForPersonSortShownSummary(personsSortedByDate, attribute), personsSortedByDate);
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
            if (getStatus.equals("Life Support")) {
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