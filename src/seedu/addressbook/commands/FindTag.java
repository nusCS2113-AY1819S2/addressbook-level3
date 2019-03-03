package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;


import seedu.addressbook.data.tag.Tag;
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTag extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose tags contain "
            + "the specified tag (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD ...\n\t"
            + "Example: " + COMMAND_WORD + " friends";



    private final Set<String> keywords;

    public FindTag(Set<String> keywords) {
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
        final List<ReadOnlyPerson> personsFound = getPersonsWithTag(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithTag(Set<String> keywords) {

        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();

        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<Tag> tags = person.getTags();
            final Set<String> tagNames = new HashSet<>();
            for(Tag t : tags) {
                tagNames.add(t.getName());
            }
            if (!Collections.disjoint(tagNames, keywords)) {
                matchedPersons.add(person);
            }


        }
        return matchedPersons;
    }

}