package seedu.addressbook.storage;

import static org.junit.Assert.assertEquals;
import static seedu.addressbook.util.TestUtil.assertTextFilesEqual;

import java.nio.file.Paths;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Age;
import seedu.addressbook.data.player.Appearance;
import seedu.addressbook.data.player.Nationality;
import seedu.addressbook.data.player.GoalsAssisted;
import seedu.addressbook.data.player.GoalsScored;
import seedu.addressbook.data.player.HealthStatus;
import seedu.addressbook.data.player.JerseyNumber;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.PositionPlayed;
import seedu.addressbook.data.player.Salary;
import seedu.addressbook.data.player.TeamName;
import seedu.addressbook.storage.StorageFile.StorageOperationException;

public class StorageFileTest {
    private static final String TEST_DATA_FOLDER = "test/data/StorageFileTest";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructor_nullFilePath_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        new StorageFile(null);
    }

    @Test
    public void constructor_noTxtExtension_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new StorageFile(TEST_DATA_FOLDER + "/" + "InvalidfileName");
    }

    @Test
    public void load_invalidFormat_exceptionThrown() throws Exception {
        // The file contains valid xml data, but does not match the AddressBook class
        StorageFile storage = getStorage("InvalidData.txt");
        thrown.expect(StorageOperationException.class);
        storage.load();
    }

    @Test
    public void load_validFormat() throws Exception {
        AddressBook actualAb = getStorage("ValidData.txt").load();
        AddressBook expectedAb = getTestAddressBook();

        // ensure loaded AddressBook is properly constructed with test data
        // TODO: overwrite equals method in AddressBook class and replace with equals method below
        assertEquals(actualAb.getAllPlayers(), expectedAb.getAllPlayers());
    }

    @Test
    public void save_nullAddressBook_exceptionThrown() throws Exception {
        StorageFile storage = getTempStorage();
        thrown.expect(NullPointerException.class);
        storage.save(null);
    }

    @Test
    public void save_validAddressBook() throws Exception {
        AddressBook ab = getTestAddressBook();
        StorageFile storage = getTempStorage();
        storage.save(ab);

        assertStorageFilesEqual(storage, getStorage("ValidData.txt"));
    }

    // getPath() method in StorageFile class is trivial so it is not tested

    /**
     * Asserts that the contents of two storage files are the same.
     */
    private void assertStorageFilesEqual(StorageFile sf1, StorageFile sf2) throws Exception {
        assertTextFilesEqual(Paths.get(sf1.getPath()), Paths.get(sf2.getPath()));
    }

    private StorageFile getStorage(String fileName) throws Exception {
        return new StorageFile(TEST_DATA_FOLDER + "/" + fileName);
    }

    private StorageFile getTempStorage() throws Exception {
        return new StorageFile(testFolder.getRoot().getPath() + "/" + "temp.txt");
    }

    private AddressBook getTestAddressBook() throws Exception {
        AddressBook ab = new AddressBook();

        ab.addPlayer(new Player(new Name("Lionel Messi"),
                                new PositionPlayed("RW"),
                                new Age("30"),
                                new Salary("200"),
                                new GoalsScored("30"),
                                new GoalsAssisted("20"),
                                new TeamName("FC Barcelona"),
                                new Nationality("Argentina"),
                                new JerseyNumber("10"),
                                new Appearance("54"),
                                new HealthStatus("Healthy"),
                                Collections.emptySet()));

        ab.addPlayer(new Player(new Name("Luis Suarez"),
                                new PositionPlayed("Striker"),
                                new Age("32"),
                                new Salary("200"),
                                new GoalsScored("30"),
                                new GoalsAssisted("20"),
                                new TeamName("FC Barcelona"),
                                new Nationality("Uruguay"),
                                new JerseyNumber("9"),
                                new Appearance("54"),
                                new HealthStatus("Healthy"),
                                Collections.emptySet()));
        return ab;
    }
}
