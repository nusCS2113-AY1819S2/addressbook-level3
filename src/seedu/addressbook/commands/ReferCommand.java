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

    public static final String MESSAGE_SUCCESS = "Patient has been successfully referred!! :D \n********************************************************************************************************\n%1$s \n********************************************************************************************************";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Set<String> keywords;
    String params[];
    Set<String> tags;
    private Person toRefer;
    public ReferCommand(Set<String> keywords) {
        this.keywords = keywords;
    }
    int count = 0;
    /**
     * Returns copy of keywords in this command.
     */
//    public Set<String> getKeywords() {
//            return new HashSet<>(keywords);
//            }
    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);


        if (count == 1) {
            try {
                addressBook.addPerson(toRefer);
            } catch (UniquePersonList.DuplicatePersonException e) {
                e.printStackTrace();
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, toRefer));
        }

        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }
//    public CommandResult execute() {
//        final List<String> personsFound = (String) getNameContainingAnyKeyword(keywords);
//        return new CommandResult(personsFound);
//    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        int index = 0;
        count = 0;
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        final List<ReadOnlyPerson> one = new ArrayList<>();
        final List<ReadOnlyPerson> emptyList = new ArrayList<>();
//            List<Person> p = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            index++;
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                count++;
                if (count == 1) {
                    one.add(person);
//                        insertionIndex = index;
                }
                matchedPersons.add(person);
            }
        }
        if (count == 1) {
            for (ReadOnlyPerson person : addressBook.getAllPersons()) {
                final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
                if (!Collections.disjoint(wordsInName, keywords)) {
                    try {
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

                    try {
                        addressBook.removePerson(person);
                    } catch (UniquePersonList.PersonNotFoundException e) { // exception for person not found
                        e.printStackTrace();
                    }
                }
            }
            return emptyList;
        } else
            return matchedPersons;
    }

}