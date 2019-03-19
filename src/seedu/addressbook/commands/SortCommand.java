package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;
import java.lang.*;


/**
 * Lists all persons in the address book in sorted order to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all persons in the address book as a list sorted in alphabetical order with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        addressBook.sorted();

        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        return new CommandResult(getMessageForPersonSortShownSummary(allPersons), allPersons);

    }
}
