package seedu.addressbook.commands.Match;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the match list in address book.
 */
public class ClearMatchCommand extends Command {

    public static final String COMMAND_WORD = "clearmatch";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clear matches in address book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Match list has been cleared!";

    @Override
    public CommandResult execute() {
        addressBook.clearMatch();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
