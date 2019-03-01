package seedu.addressbook.commands;

/**
 * Show last command.
 * Set the current command inside the input bar to last command.
 */
public class LastCommand extends Command{
    
    public static final String COMMAND_WORD = "last";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Last command typed: \n\t";
    
    public String lastCommand;
    
    public LastCommand(String last) {
        this.lastCommand = last;
    }
    
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_USAGE + lastCommand);
    }
}
