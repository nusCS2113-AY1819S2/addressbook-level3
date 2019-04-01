//@@author matthiaslum
package seedu.addressbook.commands;

public class Indicator {
    public static String lastCommand = null;

    public static void setLastCommand(String command){
        Indicator.lastCommand = command;
    }

    public static String getLastCommand(){
        return Indicator.lastCommand;
    }
}
//@@author