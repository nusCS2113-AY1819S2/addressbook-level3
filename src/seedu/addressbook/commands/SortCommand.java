package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

/**
 * Sorts the current list by name and displays the updated sorted list
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all persons in the address book as a list with index numbers, sorted by name.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allPersons = addressBook.sortAllPersons().immutableListView();
        return new CommandResult(getMessageForPersonSortShownSummary(allPersons), allPersons);
    }
}
