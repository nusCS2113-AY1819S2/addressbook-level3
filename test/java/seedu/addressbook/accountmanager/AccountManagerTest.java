package seedu.addressbook.accountmanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountManagerTest {

    private static final String SUCCESS = "Login Success, loading...";
    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String REGISTRATION_SUCCEED = "Registration succeed, please login using the username and password";
    private static final String INVALID_FORMAT = "Please login or register using: " + "\n" + "login username password" + "\n" + "register username password";
    private static final String USERNAME_REGISTERED = "The username has already been registered, please try a new username";
    private static final String WEAK_PASSWORD = "The password should contain at least one lowercase letter, one uppercase letter, and one digit";    private AccountManager accountManager = new AccountManager();

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
        final String input1 = "login Admin Admin123";
        final String input2 = "some_random_string";
        final String input3 = "login wrong_username wrong_password";
        final String input4 = "register new new123";
        final String input5 = "register new New";
        final String input6 = "register new New123";
        final String input7 = "register Admin Admin345";
        final String input8 = "login new New123";
        assertEquals(accountManager.accountCommandHandler(input1), SUCCESS);
        assertEquals(accountManager.accountCommandHandler(input2), INVALID_FORMAT);
        assertEquals(accountManager.accountCommandHandler(input3), INVALID_USERNAME_OR_PASSWORD);
        assertEquals(accountManager.accountCommandHandler(input4), WEAK_PASSWORD);
        assertEquals(accountManager.accountCommandHandler(input5), WEAK_PASSWORD);
        assertEquals(accountManager.accountCommandHandler(input6), REGISTRATION_SUCCEED);
        assertEquals(accountManager.accountCommandHandler(input7), USERNAME_REGISTERED);
        assertEquals(accountManager.accountCommandHandler(input8), SUCCESS);
    }
}