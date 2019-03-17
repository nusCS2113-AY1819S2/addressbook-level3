package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.match.ReadOnlyMatch;

import java.util.List;
import java.util.Optional;

import static seedu.addressbook.common.Messages.*;

/**
 * Main Window of the GUI.
 */
public class MainWindow {

    private Logic logic;
    private Stoppable mainApp;

    public MainWindow(){
    }

    public void setLogic(Logic logic){
        this.logic = logic;
    }

    public void setMainApp(Stoppable mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private TextArea outputConsole;

    @FXML
    private TextField commandInput;


    @FXML
    void onCommand(ActionEvent event) {
        try {
            String userCommandText = commandInput.getText();
            CommandResult result = logic.execute(userCommandText);
            if(isExitCommand(result)){
                exitApp();
                return;
            }
            displayResult(result);
            clearCommandInput();
        } catch (Exception e) {
            displayPersonResult(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void exitApp() throws Exception {
        mainApp.stop();
    }

    /** Returns true of the result given is the result of an exit command */
    private boolean isExitCommand(CommandResult result) {
        return result.feedbackToUser.equals(ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    /** Clears the command input box */
    private void clearCommandInput() {
        commandInput.setText("");
    }

    /** Clears the output displayPersonResult area */
    public void clearOutputConsole(){
        outputConsole.clear();
    }

    /** Displays the result of a command execution to the user. */
    public void displayResult(CommandResult result) {
        clearOutputConsole();
        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();
        final Optional<List<? extends ReadOnlyTeam>> resultTeams = result.getRelevantTeams();
        final Optional<List<? extends ReadOnlyMatch>> resultMatches = result.getRelevantMatches();
        if(resultPersons.isPresent()) {
            displayPersonResult(resultPersons.get());
        }
        if(resultTeams.isPresent()) {
            displayTeamResult(resultTeams.get());
        }
        if(resultMatches.isPresent()) {
            displayMatch(resultMatches.get());
        }
        displayPersonResult(result.feedbackToUser);
    }

    public void displayWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        displayPersonResult(MESSAGE_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo);
    }

    /**
     * Displays the list of matches in the output display area, formatted as an indexed list.
     */
    private void displayMatch(List<? extends ReadOnlyMatch> matches) {
        display(new Formatter().formatMatchResult(matches));
    }

    /**
     * Displays the list of persons in the output displayPersonResult area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayPersonResult(List<? extends ReadOnlyPerson> persons) {
        displayPersonResult(new Formatter().formatPersonResult(persons));
    }

    /**
     * Displays the list of teams in the output displayPersonResult area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayTeamResult(List<? extends ReadOnlyTeam> teams) {
        displayTeamResult(new Formatter().formatTeamResult(teams));
    }

    /**
     * Displays the given messages on the output displayPersonResult area, after formatting appropriately.
     */
    private void displayPersonResult(String... messages) {
        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
    }

    /**
     * Displays the given messages on the output displayTeamResult area, after formatting appropriately.
     */
    private void displayTeamResult(String... messages) {
        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
    }

}
