package seedu.addressbook.parser;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.player.*;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.*;
import seedu.addressbook.data.tag.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ParserTest {

    private Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void emptyInput_returnsIncorrect() {
        final String[] emptyInputs = { "", "  ", "\n  \n" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    @Test
    public void unknownCommandWord_returnsHelp() {
        final String input = "anyhowcommandword arguments arguments";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    /**
     * Test 0-argument commands
     */
    @Test
    public void helpCommand_parsedCorrectly() {
        final String input = "help";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    @Test
    public void clearCommand_parsedCorrectly() {
        final String input = "clear";
        parseAndAssertCommandType(input, ClearCommand.class);
    }

    @Test
    public void listCommand_parsedCorrectly() {
        final String input = "list";
        parseAndAssertCommandType(input, ListCommand.class);
    }

    @Test
    public void exitCommand_parsedCorrectly() {
        final String input = "exit";
        parseAndAssertCommandType(input, ExitCommand.class);
    }

    /**
     * Test ingle index argument commands
     */
    @Test
    public void deleteCommand_noArgs() {
        final String[] inputs = { "delete", "delete " };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "delete notANumber ", "delete 8*wh12", "delete 1 2 3 4 5" };
        final String resultMessage;
        resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delete " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewAllCommand_noArgs() {
        final String[] inputs = { "viewall", "viewall " };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewall notAnumber ", "viewall 8*wh12", "viewall 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewall " + testIndex;
        final ViewAllCommand result = parseAndAssertCommandType(input, ViewAllCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test find players by keyword in name command
     */

    @Test
    public void findCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
            "find",
            "find "
        };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "find " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "find " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add player command
     */
    @Test
    public void addCommand_invalidArgs() {
        final String[] inputs = {
            "add",
            "add ",
            "add wrong args format",
                // no position prefix
                String.format("addPlayer $s $s a/$s sal/$s gs/$s ga/$s tm/$s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),
                // no age prefix
                String.format("addPlayer $s p/$s $s sal/$s gs/$s ga/$s tm/$s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),
                // no salary prefix
                String.format("addPlayer $s p/$s a/$s $s gs/$s ga/$s tm/$s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no GoalsScored prefix
                String.format("addPlayer $s p/$s a/$s sal/$s $s ga/$s tm/$s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no GoalsAssisted prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s $s tm/$s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no Team prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s ga/$s $s ctry/$s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no Country prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s ga/$s tm/$s $s jn/$s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no JerseyNumber prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s ga/$s tm/$s ctry/$s $s app/$s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no Appearance prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s ga/$s tm/$s ctry/$s jn/$s $s hs/$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

                //no HealthStatus prefix
                String.format("addPlayer $s p/$s a/$s sal/$s gs/$s ga/$s tm/$s ctry/$s jn/$s app/$s $s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE,GoalsScored.EXAMPLE,GoalsAssisted.EXAMPLE,
                        Team.EXAMPLE,Country.EXAMPLE, JerseyNumber.EXAMPLE,Appearance.EXAMPLE,HealthStatus.EXAMPLE),

        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addCommand_invalidPlayerDataInArgs() {
        // name, age, salary, gs, ga, jn and appearance are the ones that need to be tested

        final String invalidName = "[]\\[;]";
        final String validName = Name.EXAMPLE;     //name
        final String invalidAgeArg = "a/not_numbers";
        final String validAgeArg = "a/"+ Age.EXAMPLE;        //age
        final String invalidSalaryArg = "sal/not_number" + Salary.EXAMPLE;
        final String validSalaryArg = "sal/"+ Salary.EXAMPLE;    //salary
        final String invalidGSArg = "gs/not_number";
        final String validGSArg = "gs/"+ GoalsScored.EXAMPLE;     //gs
        final String invalidGAArg = "ga/not_number";
        final String validGAArg = "ga/"+ GoalsAssisted.EXAMPLE;    //ga
        final String invalidJNArg = "jn/not_number";
        final String validJNArg = "jn/"+ JerseyNumber.EXAMPLE;
        final String invalidAppearanceArg = "app/not_number";
        final String validAppearanceArg = "app/"+Appearance.EXAMPLE;

        final String invalidTagArg = "t/invalid_-[.tag";

        // PositionPlayer, Team, Country and HealthStatus can be any string, so no invalid address
        // name, age, salary, gs, ga, jn, app

        final String addCommandFormatString = "addPlayer $s "+ PositionPlayed.EXAMPLE +" $s $s $s $s " + Team.EXAMPLE + " "
                + Country.EXAMPLE + " $s $s " + HealthStatus.EXAMPLE ;

        // test each incorrect player data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validAgeArg, validSalaryArg,validGSArg,validGAArg,validJNArg,validAppearanceArg),
                // invalid age
                String.format(addCommandFormatString, validName, invalidAgeArg, validSalaryArg,validGSArg,validGAArg,validJNArg,validAppearanceArg),
                // invalid salary
                String.format(addCommandFormatString, validName, validAgeArg, invalidSalaryArg,validGSArg,validGAArg,validJNArg,validAppearanceArg),
                // invalid GS
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg,invalidGSArg,validGAArg,validJNArg,validAppearanceArg),
                // invalid GA
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg,validGSArg,invalidGAArg,validJNArg,validAppearanceArg),
                // invalid JN
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg,validGSArg,validGAArg,invalidJNArg,validAppearanceArg),
                // invalid Appearance
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg,validGSArg,validGAArg,validJNArg,invalidAppearanceArg),
                // invalid tag
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg,validGSArg,validGAArg,validJNArg,validAppearanceArg)+ " " + invalidTagArg
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addCommand_validPlayerData_parsedCorrectly() {
        final Player testPlayer = generateTestPlayer();
        final String input = convertPlayerToAddCommandString(testPlayer);
        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPlayer(), testPlayer);
    }

    @Test
    public void addCommand_duplicateTags_merged() throws IllegalValueException {
        final Player testPlayer = generateTestPlayer();
        String input = convertPlayerToAddCommandString(testPlayer);
        for (Tag tag : testPlayer.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPlayer(), testPlayer);
    }
    /**
     * generates a test player
     */
    private static Player generateTestPlayer() {
        try {
            return new Player(
                new Name(Name.EXAMPLE),
                new PositionPlayed(PositionPlayed.EXAMPLE),
                new Age(Age.EXAMPLE),
                new Salary(Salary.EXAMPLE),
                new GoalsScored(GoalsScored.EXAMPLE),
                new GoalsAssisted(GoalsAssisted.EXAMPLE),
                new Team(Team.EXAMPLE),
                new Country(Country.EXAMPLE),
                new JerseyNumber(JerseyNumber.EXAMPLE),
                new Appearance(Appearance.EXAMPLE),
                new HealthStatus(HealthStatus.EXAMPLE),
                new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test player data should be valid by definition");
        }
    }

    /**
     * Converts player to add command string
     */
    private static String convertPlayerToAddCommandString(ReadOnlyPlayer player) {
        String addCommand = "addPlayer "
                + player.getName().fullName
                + player.getPositionPlayed().fullPosition
                + player.getAge().value
                + player.getSalary().value
                + player.getGoalsScored().value
                + player.getGoalsAssisted().value
                + player.getTeam().fullTeam
                + player.getCountry().fullCountry
                + player.getJerseyNumber().value
                + player.getAppearance().value
                + player.getHealthStatus().fullHs;
        for (Tag tag : player.getTags()) {
            addCommand += " t/" + tag.tagName;
        }
        return addCommand;
    }

    /**
     * Utility methods
     */

    /**
     * Asserts that parsing the given inputs will return IncorrectCommand with the given feedback message.
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) {
        for (String input : inputs) {
            final IncorrectCommand result = parseAndAssertCommandType(input, IncorrectCommand.class);
            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }

    /**
     * Utility method for parsing input and asserting the class/type of the returned command object.
     *
     * @param input to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */
    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }
}
