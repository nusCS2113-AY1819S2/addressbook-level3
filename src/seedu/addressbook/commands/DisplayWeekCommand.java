package seedu.addressbook.commands;

/**
 * Clears the address book.
 */
public class DisplayWeekCommand extends Command {

    public static final String COMMAND_WORD = "displayweek";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Displays the current week of the semester based on today's date.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "You are in week ?? of academic year ?? semester ??";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
