package seedu.addressbook.commands;

import seedu.addressbook.parser.generateHash;

import static seedu.addressbook.parser.Parser.TEST;
import static seedu.addressbook.parser.Parser.loginInfo;

public class SignupCommand extends Command {

    public static final String COMMAND_WORD = "signup";

    public static final String MESSAGE_SUCCESS = "Signed up!";
    public static final String MESSAGE_FAILURE = "Please Signup again!";


    public SignupCommand(String username, String password){

        String testPassword = TEST + password;
        String hashedPassword = generateHash.generateHash(testPassword);

        loginInfo.put(username, hashedPassword);

        new CommandResult(MESSAGE_SUCCESS);

    }

}
