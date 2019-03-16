package seedu.addressbook.accountmanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountManagerTest {

    private static final String SUCCESS = "Login Success, loading...";
    private static final String Invalid_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String REGISTRATION_SUCCEED = "Registration succeed, please login using the username and password";
    private static final String Invalid_FORMAT = "Please login or register using: " + "\n" + "login username password" + "\n" + "register username password";
    private static final String USERNAME_REGISTERED = "The username has already been registered, please try a new username";
    private AccountManager accountManager = new AccountManager();

    @Test
    public void setLoginStatus() {
        accountManager.setLoginStatus(true);
        assertEquals(accountManager.getLoginStatus(), true);
    }

    @Test
    public void getLoginStatus() {
        accountManager.setLoginStatus(false);
        assertEquals(accountManager.getLoginStatus(), false);
    }

    @Test
    public void checkLoginInfo() {
        final String input1 = "login guanlong 12345";
        final String input2 = "some_random_string";
        final String input3 = "login wrong_username wrong_password";
        final String input4 = "register guanlong 123";
        final String input5 = "register new new";
        final String input6 = "login new new";
        assertEquals(accountManager.accountCommandHandler(input1), SUCCESS);
        assertEquals(accountManager.accountCommandHandler(input2), Invalid_FORMAT);
        assertEquals(accountManager.accountCommandHandler(input3), Invalid_USERNAME_OR_PASSWORD);
        assertEquals(accountManager.accountCommandHandler(input4), USERNAME_REGISTERED);
        assertEquals(accountManager.accountCommandHandler(input5), REGISTRATION_SUCCEED);
        assertEquals(accountManager.accountCommandHandler(input6), SUCCESS);
    }
}