package seedu.addressbook.commands;

import java.util.List;
import seedu.addressbook.parser.Parser;

public class AllCommand extends Command {

    public static final String COMMAND_WORD = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Total number of commands entered.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Number of all commands entered:";

    @Override
    public CommandResult execute() {

        int totalnumber = Parser.allcommands;
        return new CommandResult(MESSAGE_SUCCESS+totalnumber);
    }
}
