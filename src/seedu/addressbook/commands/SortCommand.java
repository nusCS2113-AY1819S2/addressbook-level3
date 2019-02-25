package seedu.addressbook.commands;

import static seedu.addressbook.commands.Command.getMessageForPersonListShownSummary;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all persons in the address book as a list in sorted order.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        addressBook.sortPersons();
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        return new CommandResult(getMessageForPersonSortedShownSummary(allPersons), allPersons);
    }
}
