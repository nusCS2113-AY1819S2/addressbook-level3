package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import java.lang.String;
import java.util.*;

public class FindpCommand extends Command{
    public static final String COMMAND_WORD = "findp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose phone number matches"
            + "the specified phone number and displays them as a list with index numbers.\n\t"
            + "Parameters: PHONE NUMBER\n\t"
            + "Example: " + COMMAND_WORD + " 12345678";

    private final String phone_number;

    public FindpCommand(String phone_number) {
        this.phone_number = phone_number;
    }
    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithPhoneNumber(phone_number);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }
    private List<ReadOnlyPerson> getPersonsWithPhoneNumber(String phone_number) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String phone_number_of_person = person.getPhone().value;
            if (phone_number_of_person.equals(phone_number)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

}