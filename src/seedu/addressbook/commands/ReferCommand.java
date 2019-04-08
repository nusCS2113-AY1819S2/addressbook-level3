package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;

import java.util.*;

public class ReferCommand extends Command {

    public static final String COMMAND_WORD = "refer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS = "Patient %2$s has been successfully referred to %3$s!! :D\n\n********************************************************************************************************\n%1$s \n********************************************************************************************************";
    public static final String MESSAGE_NO_SUCH_PERSON = "%1$s\nThis patient does not exists in the address book records.";
    public static final String MESSAGE_TEST = "%1$s%1$s%1$s\n%2$s%2$s%2$s\n";

    private final Set<String> keywords;
    String params[];
    Set<String> tags;
    private Person toRefer;
    int count = 0;

    String string;
    String str;
    String strName;

    public ReferCommand(Set<String> keywords) {
        this.keywords = keywords;
    }
    /**
     * Returns copy of keywords in this command.
     */
//    public Set<String> getKeywords() {
//            return new HashSet<>(keywords);
//            }
    @Override
    public CommandResult execute() {

        string = String.join(" ", keywords);
//        str = string.replaceAll("\\s+", "");
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);

//        // test
//        return new CommandResult(String.format(MESSAGE_NO_SUCH_PERSON, toRefer.getName().toString()));

        if (count == 0) {
//            String string = String.join(" ", keywords);
            return new CommandResult(String.format(MESSAGE_NO_SUCH_PERSON, string));
        }
        if (count == 1) {
            try { // add updated entry
                addressBook.addPerson(toRefer);
            } catch (UniquePersonList.DuplicatePersonException e) {
                e.printStackTrace();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRefer, toRefer.getName(), toRefer.getDoctor()));
        }
        return new CommandResult(getMessageForPersonReferShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        count = 0;
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        final List<ReadOnlyPerson> emptyList = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                if (wordsInName.equals(keywords)) {
                    try {  // get particulars of the old entry
                        toRefer = new Person(
                                person.getName(),
                                person.getPhone(),
                                person.getEmail(),
                                person.getAddress(),
                                person.getAppointment(),
                                new Doctor("Dr Who"),
                                new Status("Referred"),
                                person.getTags()
                        );
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                    try { // remove old entry
                        addressBook.removePerson(person);
                    } catch (UniquePersonList.PersonNotFoundException e) { // exception for person not found
                        e.printStackTrace();
                    }
                    count = 1;
                    return emptyList;
                }

                count++;
                if (count == 1) {
                    try {  // get particulars of the old entry
                        toRefer = new Person(
                                person.getName(),
                                person.getPhone(),
                                person.getEmail(),
                                person.getAddress(),
                                person.getAppointment(),
                                new Doctor("Dr Seuss"),
                                new Status("Referred"),
                                person.getTags()
                        );
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                }
                matchedPersons.add(person);
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