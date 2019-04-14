package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;

import java.util.*;

public class ReferCommand extends Command {

    public static final String COMMAND_WORD = "refer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Refers patient specified to a default doctor (format 1) or a specific doctor (format 2)"
            + "If there are multiple entries, prints them out and prompts user to re-enter full name of the target patient.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example of format 1: " + COMMAND_WORD + " John Doe\n\t"
            + "Example of format 2: " + COMMAND_WORD + " d/Dr Wong p/ John Doe";

    public static final String MESSAGE_REFER_SUCCESS = "Patient %2$s has been successfully referred to %3$s!! :D\n\n********************************************************************************************************\n%1$s \n********************************************************************************************************";
    public static final String MESSAGE_NO_SUCH_PERSON = "%1$s\nThis patient does not exists in the address book records.";
    public static final String MESSAGE_INVALID_DOCTOR_NAME = "Doctor's names should only contain spaces and/or alphanumeric characters\nSpecial characters like . ! @ # , etc are not allowed!\nPlease re-enter with an appropriate doctor name.";

    private final Set<String> keywords;
    private static volatile String referraldoctor = "Dr Seuss";
//    String params[];
//    Set<String> tags;
    private Person toRefer;
    int count = 0;

    String string;
//    String str;
//    String strName;

    public ReferCommand(Set<String> keywords) {
        this.keywords = keywords;
    }
    /**
     * Returns copy of keywords in this command.
     */

    public ReferCommand(String name, String doctorname) {
        final String[] names = name.split("\\s+");
        final Set<String> keywords = new HashSet<>(Arrays.asList(names));
        this.keywords = keywords;
        referraldoctor = doctorname;
    }

    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {

        string = String.join(" ", keywords);
        final List<ReadOnlyPerson> personsFound = getPatientToRefer(keywords);

        if (count == 0) {
            return new CommandResult(String.format(MESSAGE_NO_SUCH_PERSON, string));
        }
        if (count == 1) {
            try { // add updated entry
                addressBook.addPerson(toRefer);
            } catch (UniquePersonList.DuplicatePersonException e) {
                e.printStackTrace();
            }
            return new CommandResult(String.format(MESSAGE_REFER_SUCCESS, toRefer, toRefer.getName(), toRefer.getDoctor()));
        }
        return new CommandResult(getMessageForPersonReferShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPatientToRefer(Set<String> keywords) {
        count = 0;
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        final List<ReadOnlyPerson> emptyList = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                if (wordsInName.equals(keywords)) { // if a full name match is found, it is THE entry


                    Set<Tag> tagSet = person.getTags(); // get all current tags
                    try {
                        tagSet.add(new Tag("referred")); // add a refer tag
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }

                    try {  // store particulars of target entry
                        toRefer = new Person(
                                person.getName(),
                                person.getPhone(),
                                person.getEmail(),
                                person.getAddress(),
                                person.getAppointment(),
                                new Doctor(referraldoctor),
                                person.getStatus(),
                                tagSet
                        );
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }


                    try { // remove target entry
                        addressBook.removePerson(person);
                    } catch (UniquePersonList.PersonNotFoundException e) { // exception for person not found
                        e.printStackTrace();
                    }
                    count = 1;
                    return emptyList; // exit method
                }

                count++;
                if (count == 1) { // the first matched entry
                    try {  // store particulars of the first matched entry
                        toRefer = new Person(
                                person.getName(),
                                person.getPhone(),
                                person.getEmail(),
                                person.getAddress(),
                                person.getAppointment(),
                                new Doctor(referraldoctor),
                                person.getStatus(),
                                person.getTags()
                        );
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                }
                matchedPersons.add(person); // add patient into set of matching entries
            }
        }
        if (count == 1) {
            for (ReadOnlyPerson person : addressBook.getAllPersons()) {
                final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
                if (!Collections.disjoint(wordsInName, keywords)) {

                    try { // remove old entry
                        addressBook.removePerson(person);
                    } catch (UniquePersonList.PersonNotFoundException e) { // exception for person not found
                        e.printStackTrace();
                    }
                }
            }
            return emptyList;
        }

        else
            return matchedPersons;
    }

}
