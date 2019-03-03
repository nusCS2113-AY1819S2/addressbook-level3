package seedu.addressbook.commands;

public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "Logged out!";

    @Override
    public CommandResult execute(){

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
