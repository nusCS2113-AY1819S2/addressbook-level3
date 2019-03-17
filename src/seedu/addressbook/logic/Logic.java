package seedu.addressbook.logic;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.storage.StorageFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic {


    private StorageFile storage;
    private AddressBook addressBook;

    /** The list of person shown to the user most recently.  */
   private List<? extends ReadOnlyPerson> lastPersonShownList = Collections.emptyList();
  
    /** The list of match shown to the user most recently.  */
    private List<? extends ReadOnlyMatch> lastMatchList = Collections.emptyList();
    
    private List<? extends ReadOnlyTeam> lastTeamShownList = Collections.emptyList();

    public Logic() throws Exception{
        setStorage(initializeStorage());
        setAddressBook(storage.load());
    }

    Logic(StorageFile storageFile, AddressBook addressBook){
        setStorage(storageFile);
        setAddressBook(addressBook);
    }

    void setStorage(StorageFile storage){
        this.storage = storage;
    }

    void setAddressBook(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile.InvalidStorageFilePathException if the target file path is incorrect.
     */
    private StorageFile initializeStorage() throws StorageFile.InvalidStorageFilePathException {
        return new StorageFile();
    }

    public String getStorageFilePath() {
        return storage.getPath();
    }

    /**
     * Unmodifiable view of the current last person list.
     */
    public List<ReadOnlyPerson> getLastPersonShownList() {
        return Collections.unmodifiableList(lastPersonShownList);
    }

    /**
     * Unmodifiable view of the current last match list.
     */
    public List<ReadOnlyMatch> getLastMatchList() {
        return Collections.unmodifiableList(lastMatchList);
    }

    protected void setLastMatchList(List<? extends ReadOnlyMatch> newList) {
        lastMatchList = newList;
    }

    protected void setLastPersonShownList(List<? extends ReadOnlyPerson> newList) {
        lastPersonShownList = newList;
    }

    /**
     * Unmodifiable view of the current last shown list(Team).
     */
    public List<ReadOnlyTeam> getLastTeamShownList() {
        return Collections.unmodifiableList(lastTeamShownList);
    }

    protected void setLastTeamShownList(List<? extends ReadOnlyTeam> newList) {
        lastTeamShownList = newList;
    }

    /**
     * Parses the user command, executes it, and returns the result.
     * @throws Exception if there was any problem during command execution.
     */
    public CommandResult execute(String userCommandText) throws Exception {
        Command command = new Parser().parseCommand(userCommandText);
        CommandResult result = execute(command);
        recordResult(result);
        return result;
    }

    /**
     * Executes the command, updates storage, and returns the result.
     *
     * @param command user command
     * @return result of the command
     * @throws Exception if there was any problem during command execution.
     */
    private CommandResult execute(Command command) throws Exception {
        command.setData(addressBook,
                lastPersonShownList,
                lastTeamShownList,
                lastMatchList);
        CommandResult result = command.execute();
        storage.save(addressBook);
        return result;
    }

    /** Updates the {@link #lastPersonList} if the result contains a list of Persons.
     *  Updates the {@link #lastMatchList} if the result contains a list of Matches.
     */      
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> personList = result.getRelevantPersons();
        final Optional<List<? extends ReadOnlyTeam>> teamList = result.getRelevantTeams();
        final Optional<List<? extends ReadOnlyMatch>> matchList = result.getRelevantMatches();
        if (personList.isPresent()) {
            lastPersonShownList = personList.get();
        } else if (teamList.isPresent()) {
            lastTeamShownList = teamList.get();
        } else if (matchList.isPresent()) {
            lastMatchList = matchList.get();
        }
    }
}
