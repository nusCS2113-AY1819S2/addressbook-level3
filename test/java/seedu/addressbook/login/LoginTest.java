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
    private static final String Invalid_FORMAT = "Please input the correct login command: login username password";
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
        assertEquals(login.checkLoginInfo(input1), SUCCESS);
        assertEquals(login.checkLoginInfo(input2), Invalid_FORMAT);
        assertEquals(login.checkLoginInfo(input3), Invalid_USERNAME_OR_PASSWORD);
    }
}