package seedu.addressbook.login;
package seedu.addressbook.accountmanager;

import java.util.Map;
import java.util.HashMap;

public class AccountManager {

    private boolean loginStatus;

    public static final String LOGIN_PROMPT = "Please login or register in order to use the address book";
    private static final String SUCCESS = "Login Success, loading...";
    private static final String REGISTRATION_SUCCEED = "Registration succeed, please login using the username and password";
    private static final String Invalid_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String Invalid_FORMAT = "Please login or register using: " + "\n" + "login username password" + "\n" + "register username password";
    private static final String USERNAME_REGISTERED = "The username has already been registered, please try a new username";
    private Map<String, String> accounts = new HashMap<String, String>();


    public AccountManager(){
        this.loginStatus = false;
        this.accounts.put("guanlong", "12345");
        this.accounts.put("doctorA", "doctorA");
    }

    public void setLoginStatus(boolean newStatus){
        this.loginStatus = newStatus;
    }

    public boolean getLoginStatus(){
        return loginStatus;
    }

    public String accountCommandHandler(String userCommandText){
        String[] accountInfo = userCommandText.split(" ");
        try {
            if (accountInfo.length == 3 && accountInfo[0].equals("login")) {
                if (accounts.containsKey(accountInfo[1]) && accounts.get(accountInfo[1]).equals(accountInfo[2])) {
                    this.loginStatus = true;
                    return SUCCESS;
                }
                else {
                    return Invalid_USERNAME_OR_PASSWORD;
                }
            }
            else{
                if (accountInfo.length == 3 && accountInfo[0].equals("register")){
                    String username = accountInfo[1];
                    if (accounts.containsKey(accountInfo[1]))
                    {
                        return(USERNAME_REGISTERED);
                    }
                    String password = accountInfo[2];
                    register(username, password);
                    return REGISTRATION_SUCCEED;
                }
                else{
                    return Invalid_FORMAT;
                }
            }
        }
        catch (Exception e){
            return Invalid_FORMAT;
        }
    }

    private void register(String username, String password){
        accounts.put(username, password);
    }
}
