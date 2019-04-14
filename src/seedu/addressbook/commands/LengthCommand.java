package seedu.addressbook.commands;

public class LengthCommand extends Command {
    public static final String COMMAND_WORD = "length";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Returns the current length of the address book at the point of query.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Length of the address book is: ";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS + addressBook.size());
    }
}