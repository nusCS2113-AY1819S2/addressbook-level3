package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;
import java.util.*;


public class RandomCommand extends Command {

    public static final String COMMAND_WORD = "random";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Display a random person and their details in the address book. \n\t"
            + "Example: " + COMMAND_WORD;


    @Override

    public CommandResult execute() {
        final List<ReadOnlyPerson> randomPerson = getRandomPerson();
        return new CommandResult(getMessageForRandomShown(), randomPerson);
    }


    private List<ReadOnlyPerson> getRandomPerson() {
        final List<ReadOnlyPerson> randomEntry = new ArrayList<>();
        Random random = new Random();
        List<ReadOnlyPerson> allPerson = addressBook.getAllPersons().immutableListView();
        int upperBound = allPerson.size();
        int index = 0;
        int randomIndex = random.nextInt(upperBound);

        for(ReadOnlyPerson person : allPerson) {
            if (index == randomIndex) {
                randomEntry.add(person);
                break;
            }
            index++;
        }
        return randomEntry;
    }



}