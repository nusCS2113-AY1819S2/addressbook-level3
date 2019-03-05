package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Lists a random person in the address book to the user.
 */
public class RandomCommand extends Command {

    public static final String COMMAND_WORD = "random";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays a random person from the address book.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "We have generated a random person for you.";
    public static final String MESSAGE_FAILURE = "You must have at least one person in the address book.";


    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        final List<ReadOnlyPerson> randomPerson = new ArrayList<>();
        if (allPersons.size() > 0) {
            ReadOnlyPerson random = allPersons.get(new Random().nextInt(allPersons.size()));
            randomPerson.add(random);
            return new CommandResult(MESSAGE_SUCCESS, randomPerson);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
