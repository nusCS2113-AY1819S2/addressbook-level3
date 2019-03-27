package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.commands.player.*;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.player.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.storage.StorageFile;

public class LogicTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private StorageFile saveFile;
    private AddressBook addressBook;
    private Logic logic;

    @Before
    public void setup() throws Exception {
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath());
        addressBook = new AddressBook();
        saveFile.save(addressBook);
        logic = new Logic(saveFile, addressBook);
    }

    @Test
    public void constructor() {
        //Constructor is called in the setup() method which executes before every test, no need to call it here again.

        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastPlayerShownList());
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, AddressBook.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    private void assertCommandBehavior(String inputCommand,
                                      String expectedMessage,
                                      AddressBook expectedAddressBook,
                                      boolean isRelevantPlayersExpected,
                                      List<? extends ReadOnlyPlayer> lastPlayerList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantPlayers().isPresent(), isRelevantPlayersExpected);
        if (isRelevantPlayersExpected) {
            assertEquals(lastPlayerList, r.getRelevantPlayers().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedAddressBook, addressBook);
        assertEquals(lastPlayerList, logic.getLastPlayerShownList());
        assertEquals(addressBook, saveFile.load());
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "thisisnonsensebutyeahwhocares";
        assertCommandBehavior(unknownCommand, HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        addressBook.addPlayer(helper.generatePlayer(1));
        addressBook.addPlayer(helper.generatePlayer(2));
        addressBook.addPlayer(helper.generatePlayer(3));
        addressBook.addPlayer(helper.generatePlayer(4));
        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, AddressBook.empty(),
                false, Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoPositionPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker 30 sal/20000 gs/0 ga/0 tm/validTeam.butNoAgePrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 20000 gs/0 ga/0 tm/validTeam.butNoSalaryPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 0 ga/0 tm/validTeam.butNoGoalsScoredPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 0 tm/validTeam.butNoGoalsAssistedPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 ga/0 validTeam.butNoPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoCountryPrefix China" +
                        "jn/9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoJerseyNumberPrefix ctry/China" +
                        "9 app/0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoAppearancePrefix ctry/China" +
                        "jn/9 0 hs/Healthy", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoHealthStatusPrefix ctry/China" +
                        "jn/9 app/0 Healthy", expectedMessage);
        // need another test function for addFast
        }

    @Test
    public void execute_addFast_invalidArgsFormat() throws Exception{
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFastCommand.MESSAGE_USAGE);

        assertCommandBehavior(
                "addFast ValidName Striker a/30 sal/20000 tm/FC_NUS.butNoPositionPrefix ctry/Singapore jn/10",
                expectedMessage);

        assertCommandBehavior(
                "addFast ValidName p/Striker 30 sal/20000 tm/FC_NUS.butNoAgePrefix ctry/Singapore jn/10",
                expectedMessage);

        assertCommandBehavior(
                "addFast ValidName p/Striker a/30 20000 tm/FC_NUS.butNoSalaryPrefix ctry/Singapore jn/10",
                expectedMessage);

        assertCommandBehavior(
                "addFast ValidName p/Striker a/30 sal/20000 FC_NUS.butNoPrefix ctry/Singapore jn/10",
                expectedMessage);

        assertCommandBehavior(
                "addFast ValidName p/Striker a/30 sal/20000 tm/FC_NUS.butNoCountryPrefix Singapore jn/10",
                expectedMessage);

        assertCommandBehavior(
                "addFast ValidName p/Striker a/30 sal/20000 tm/FC_NUS.butNoJerseyNumberPrefix ctry/Singapore 10",
                expectedMessage);

    }

"add Valid Name Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoPositionPrefix ctry/China" +
        "jn/9 app/0 hs/Healthy"
    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/Striker a/30 sal/20000 gs/0 ga/0 tm/validTeam.butNoPositionPrefix ctry/China" +
                        "jn/9 app/0 hs/Healthy", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                              String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                              expectedAb,
                              false,
                              Collections.emptyList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(toBeAdded);

        // setup starting state
        addressBook.addPerson(toBeAdded); // player already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PLAYER,
                expectedAb,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expectedAb = helper.generateAddressBook(false, true);
        List<? extends ReadOnlyPerson> expectedList = expectedAb.getAllPersons().immutableListView();

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);

        assertCommandBehavior("list",
                              Command.getMessageForPersonListShownSummary(expectedList),
                              expectedAb,
                              true,
                              expectedList);
    }



    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single player in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single player in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Person> lastPersonList = helper.generatePersonList(false, true);

        logic.setLastPlayerShownList(lastPersonList);

        assertCommandBehavior(commandWord + " -1", expectedMessage, AddressBook.empty(), false, lastPersonList);
        assertCommandBehavior(commandWord + " 0", expectedMessage, AddressBook.empty(), false, lastPersonList);
        assertCommandBehavior(commandWord + " 3", expectedMessage, AddressBook.empty(), false, lastPersonList);

    }


    @Test
    public void execute_tryToViewMissingPerson_errorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePlayer(1, false);
        Person p2 = helper.generatePlayer(2, false);
        List<Person> lastPersonList = helper.generatePersonList(p1, p2);

        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(p2);

        addressBook.addPerson(p2);
        logic.setLastPlayerShownList(lastPersonList);

        assertCommandBehavior("view 1",
                              Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE,
                              expectedAb,
                              false,
                              lastPersonList);
    }

    @Test
    public void execute_viewAll_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewall ", expectedMessage);
        assertCommandBehavior("viewall arg not number", expectedMessage);
    }

    @Test
    public void execute_viewAll_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewall");
    }

    @Test
    public void execute_tryToViewAllPersonMissingInAddressBook_errorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePlayer(1, false);
        Person p2 = helper.generatePlayer(2, false);
        List<Person> lastPersonList = helper.generatePersonList(p1, p2);

        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(p1);

        addressBook.addPerson(p1);
        logic.setLastPlayerShownList(lastPersonList);

        assertCommandBehavior("viewall 2",
                                Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE,
                                expectedAb,
                                false,
                                lastPersonList);
    }

    @Test
    public void execute_delete_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("delete ", expectedMessage);
        assertCommandBehavior("delete arg not number", expectedMessage);
    }

    @Test
    public void execute_delete_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePlayer(1, false);
        Person p2 = helper.generatePlayer(2, true);
        Person p3 = helper.generatePlayer(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expectedAb = helper.generateAddressBook(threePersons);
        expectedAb.removePerson(p2);


        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastPlayerShownList(threePersons);

        assertCommandBehavior("delete 2",
                                String.format(DeleteCommand.MESSAGE_DELETE_PLAYER_SUCCESS, p2),
                                expectedAb,
                                false,
                                threePersons);
    }

    @Test
    public void execute_delete_missingInAddressBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePlayer(1, false);
        Person p2 = helper.generatePlayer(2, true);
        Person p3 = helper.generatePlayer(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expectedAb = helper.generateAddressBook(threePersons);
        expectedAb.removePerson(p2);

        helper.addToAddressBook(addressBook, threePersons);
        addressBook.removePerson(p2);
        logic.setLastPlayerShownList(threePersons);

        assertCommandBehavior("delete 2",
                                Messages.MESSAGE_PLAYER_NOT_IN_LEAGUE,
                                expectedAb,
                                false,
                                threePersons);
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("KE Y");
        Person p2 = helper.generatePersonWithName("KEYKEYKEY sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAb = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAb,
                                true,
                                expectedList);
    }

    @Test
    public void execute_find_isCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAb = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAb,
                                true,
                                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAb = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY rAnDoM",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAb,
                                true,
                                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {
        /**
         * generate a person with the stated parameters
         */
        Person adam() throws Exception {
            Name name = new Name("Adam Brown");
            Phone privatePhone = new Phone("111111", true);
            Email email = new Email("adam@gmail.com", false);
            Address privateAddress = new Address("111, alpha street", true);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
            return new Person(name, privatePhone, email, privateAddress, tags);
        }

        /**
         * Generates a valid player using the given seed.
         * Running this function with the same parameter values guarantees the returned player will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the player data field values
         *
         * */
        Player generatePlayer(int seed) throws Exception {
            return new Player(
                    new Name("Person " + seed),
                    new PositionPlayed("Position" + seed),
                    new Age(""+ Math.abs(seed)),
                    new Salary(""+Math.abs(seed)),
                    new Team("Team"+Math.abs(seed)),
                    new Country("Country"+Math.abs(seed)),
                    new JerseyNumber(""+(Math.abs(seed)%35)),
                    new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))))
            );
        }

        /** Generates the correct add command based on the player given */
        String generateAddCommand(Person p) {
            StringJoiner cmd = new StringJoiner(" ");

            cmd.add("add");

            cmd.add(p.getName().toString());
            cmd.add((p.getPhone().isPrivate() ? "pp/" : "p/") + p.getPhone());
            cmd.add((p.getEmail().isPrivate() ? "pe/" : "e/") + p.getEmail());
            cmd.add((p.getAddress().isPrivate() ? "pa/" : "a/") + p.getAddress());

            Set<Tag> tags = p.getTags();
            for (Tag t: tags) {
                cmd.add("t/" + t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        AddressBook generateAddressBook(Boolean... isPrivateStatuses) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, isPrivateStatuses);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Person> persons) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, persons);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         * @param isPrivateStatuses flags to indicate if all contact details of generated persons should be set to
         *                          private.
         */
        void addToAddressBook(AddressBook addressBook, Boolean... isPrivateStatuses) throws Exception {
            addToAddressBook(addressBook, generatePersonList(isPrivateStatuses));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception {
            for (Person p: personsToAdd) {
                addressBook.addPerson(p);
            }
        }

        /**
         * Creates a list of Persons based on the give Person objects.
         */
        List<Person> generatePersonList(Person... persons) throws Exception {
            List<Person> personList = new ArrayList<>();
            for (Person p: persons) {
                personList.add(p);
            }
            return personList;
        }

        /**
         * Generates a list of Persons based on the flags.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        List<Person> generatePersonList(Boolean... isPrivateStatuses) throws Exception {
            List<Person> persons = new ArrayList<>();
            int i = 1;
            for (Boolean p: isPrivateStatuses) {
                persons.add(generatePlayer(i++, p));
            }
            return persons;
        }

        /**
         * Generates a Person object with given name. Other fields will have some dummy values.
         */
        Person generatePersonWithName(String name) throws Exception {
            return new Person(
                    new Name(name),
                    new Phone("1", false),
                    new Email("1@email", false),
                    new Address("House of 1", false),
                    Collections.singleton(new Tag("tag"))
            );
        }
    }
}
