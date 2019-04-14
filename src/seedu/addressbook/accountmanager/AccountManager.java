//@@author liguanlong
package seedu.addressbook.accountmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class AccountManager {

    private boolean loginStatus;
    private String currentAccount;
    private Map<String, String> accounts = new HashMap<String, String>();

    // All the string constants used in the class
    public static final String LOGIN_PROMPT = "Please login or register in order to use the address book";
    public static final String LOGOUT_MESSAGE_USAGE = "logout" + ":\n"
            + "Log the current user out.\n\t"
            + "Example: " + "logout";
    private static final String SUCCESS = "Login Success, loading...";
    private static final String REGISTRATION_SUCCEED = "Registration succeed, please login using the username and password";
    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String INVALID_FORMAT = "Please login or register using: " + "\n" + "login username password" + "\n" + "register username password";
    private static final String USERNAME_REGISTERED = "The username has already been registered, please try a new username";
    private static final String WEAK_PASSWORD = "The password should contain at least one lowercase letter, one uppercase letter, and one digit";


    public AccountManager(){
        this.loginStatus = false;
        loadAccounts();
    }

    public boolean getLoginStatus(){
        return loginStatus;
    }

    public String getCurrentAccount(){
        return currentAccount;
    }

    public String accountCommandHandler(String userCommandText){
        String[] accountInfo = userCommandText.split(" ");
        try {
            // check if command is exit
            if (accountInfo.length == 1 && accountInfo[0].equals("exit")){
                storeAccounts();
                System.exit(0);
            }
            // check if command is login
            if (accountInfo.length == 3 && accountInfo[0].equals("login")) {
                // check if username and password are correct
                if (this.accounts.containsKey(accountInfo[1]) && this.accounts.get(accountInfo[1]).equals(accountInfo[2])) {
                    this.currentAccount = accountInfo[1];
                    setLoginStatus(true);
                    return SUCCESS;
                }
                else {
                    return INVALID_USERNAME_OR_PASSWORD;
                }
            }
            // check if command is register
            if (accountInfo.length == 3 && accountInfo[0].equals("register")){
                String username = accountInfo[1];
                // if the username has already been registered
                if (this.accounts.containsKey(accountInfo[1])) {
                    return USERNAME_REGISTERED;
                }
                String password = accountInfo[2];
                // if the password does not contain at least one lowercase letter, one uppercase letter and one digit
                if (weakPassword(password)){
                    return WEAK_PASSWORD;
                }
                register(username, password);
                return REGISTRATION_SUCCEED;
            }
            // if no match with any command, return invalid format message
            return INVALID_FORMAT;

        }
        catch (Exception e){
            return INVALID_FORMAT;
        }
    }

    // load all the accounts from accounts.properties to the HashMap accounts
    private void loadAccounts() {
        try {
            Properties accountsFile = new Properties();
            accountsFile.load(new FileInputStream("accounts.properties"));
            for (String key : accountsFile.stringPropertyNames()){
                this.accounts.put(key, accountsFile.get(key).toString());
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // store all the accounts in HashMap accounts to accounts.properties
    public void storeAccounts() {
        try {
            Properties accountsFile = new Properties();
            for (Map.Entry<String, String> entry: this.accounts.entrySet()){
                accountsFile.put(entry.getKey(), entry.getValue());
            }
            accountsFile.store(new FileOutputStream("accounts.properties"), null);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void logout(){
        setLoginStatus(false);
        storeAccounts();
    }

    public void setLoginStatus(boolean status){
        this.loginStatus = status;
    }

    private void register(String username, String password){
        this.accounts.put(username, password);
        storeAccounts();
    }

    private boolean weakPassword(String password){
        return password.toLowerCase().equals(password) || doesNotContainDigit(password);
    }

    private boolean doesNotContainDigit(String password){
        for (char character : password.toCharArray()) {
            if (Character.isDigit(character)){
                return false;
            }
        }
        return true;
    }
}

