package seedu.addressbook.login;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import seedu.addressbook.login.Login;

public class LoginTest {

    private static final String SUCCESS = "Login Success, loading...";
    private static final String Invalid_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String REGISTRATION_SUCCEED = "Registration succeed, please login using the username and password";
    private static final String Invalid_FORMAT = "Please login or register using: " + "\n" + "login username password" + "\n" + "register username password";
    private static final String USERNAME_REGISTERED = "The username has already been registered, please try a new username";
    private Login login = new Login();

    @Test
    public void setLoginStatus() {
        login.setLoginStatus(true);
        assertEquals(login.getLoginStatus(), true);
    }

    @Test
    public void getLoginStatus() {
        login.setLoginStatus(false);
        assertEquals(login.getLoginStatus(), false);
    }

    @Test
    public void checkLoginInfo() {
        final String input1 = "login guanlong 12345";
        final String input2 = "some_random_string";
        final String input3 = "login wrong_username wrong_password";
        final String input4 = "register guanlong 123";
        final String input5 = "register new new";
        final String input6 = "login new new";
        assertEquals(login.checkLoginInfo(input1), SUCCESS);
        assertEquals(login.checkLoginInfo(input2), Invalid_FORMAT);
        assertEquals(login.checkLoginInfo(input3), Invalid_USERNAME_OR_PASSWORD);
        assertEquals(login.checkLoginInfo(input4), USERNAME_REGISTERED);
        assertEquals(login.checkLoginInfo(input5), REGISTRATION_SUCCEED);
        assertEquals(login.checkLoginInfo(input6), SUCCESS);
    }
}