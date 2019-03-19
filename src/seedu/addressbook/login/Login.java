package seedu.addressbook.login;

import java.util.Map;
import java.util.HashMap;

public class Login {

    private boolean loginStatus;

    public static final String LOGIN_PROMPT = "Please login in order to use the address book";
    private static final String SUCCESS = "Login Success, loading...";
    private static final String Invalid_USERNAME_OR_PASSWORD = "Invalid username or password, please try again";
    private static final String Invalid_FORMAT = "Please input the correct login command: login username password";
    private Map<String, String> accounts = new HashMap<String, String>();


    public Login(){
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

    public String checkLoginInfo(String userCommandText){
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
                return Invalid_FORMAT;
            }
        }
        catch (Exception e){
            return Invalid_FORMAT;
        }
    }
}
