package seedu.addressbook.commands;

/**
 * Clears the person list in address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clear persons in address book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Person list has been cleared!";

    @Override
    public CommandResult execute() {
        addressBook.clearPerson();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
