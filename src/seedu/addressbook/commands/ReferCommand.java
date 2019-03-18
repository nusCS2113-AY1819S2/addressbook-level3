package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

public class ReferCommand extends Command {

    public static final String COMMAND_WORD = "refer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

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
    public CommandResult execute() throws IllegalValueException {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
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
            int count = 0;
            final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
            final List<ReadOnlyPerson> one = new ArrayList<>();
            List<Person> p = new ArrayList<>();
            for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
                if (!Collections.disjoint(wordsInName, keywords)) {
                    count++;
                    if (count == 1) {
                        one.add(person);
                    }
                    matchedPersons.add(person);
                }
            }
            if (count == 1) {
//              Person.ReferTo("Dr. Teo").ReferTo("Dr. Teo");
//                Person.Refer();
                return one;
            }
            else
                return matchedPersons;
    }

//    @Override
//    public CommandResult execute() {
//        try {
//            final ReadOnlyPerson target = getTargetPerson();
//            if (!addressBook.containsPerson(target)) {
//                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
//            }
//            return new CommandResult(String.format(MESSAGE_VIEW_PERSON_DETAILS, target.getAsTextShowAll()));
//        } catch (IndexOutOfBoundsException ie) {
//            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//    }



//    @Override

//    public CommandResult execute() {
//        final List<Name> personsFound = getNameContainingAnyKeyword(keywords);
//        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
//    }

//    private List<Name> getNameContainingAnyKeyword(Set<String> keywords) {
//        final List<Name> matchedPersons = new ArrayList<>();
//        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
//            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
//            if (!Collections.disjoint(wordsInName, keywords)) {
//                matchedPersons.add(person.getName());
//            }
//        }
//        return matchedPersons;
//    }
}
