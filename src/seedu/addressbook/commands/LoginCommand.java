package seedu.addressbook.commands;

import seedu.addressbook.parser.generateHash;
import static seedu.addressbook.parser.Parser.TEST;
import static seedu.addressbook.parser.Parser.loginInfo;

public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_SUCCESS = "Logged in!";
    public static final String MESSAGE_FAILURE = "Login failed!";

    public LoginCommand(String username, String password){

        Boolean isAuthenticated = false;

        String testPassword = TEST + password;
        String hashedPassword = generateHash.generateHash(testPassword);

        String storedPassword = loginInfo.get(username);
        if (hashedPassword.equals(storedPassword)){
            isAuthenticated = true;
            new CommandResult(MESSAGE_SUCCESS);
        } else {
            isAuthenticated = false;
            new CommandResult(MESSAGE_FAILURE);
        }
    }
}
