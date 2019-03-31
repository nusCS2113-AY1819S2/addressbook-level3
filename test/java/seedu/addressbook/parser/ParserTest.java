package seedu.addressbook.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.player.AddCommand;
import seedu.addressbook.commands.player.ClearCommand;
import seedu.addressbook.commands.player.DeleteCommand;
import seedu.addressbook.commands.player.FindCommand;
import seedu.addressbook.commands.player.ListCommand;
import seedu.addressbook.commands.player.ViewAllCommand;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Age;
import seedu.addressbook.data.player.Appearance;
import seedu.addressbook.data.player.GoalsAssisted;
import seedu.addressbook.data.player.GoalsScored;
import seedu.addressbook.data.player.HealthStatus;
import seedu.addressbook.data.player.JerseyNumber;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Nationality;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.PositionPlayed;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.Salary;
import seedu.addressbook.data.player.TeamName;
import seedu.addressbook.data.tag.Tag;

public class ParserTest {

    private Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void emptyInput_returnsIncorrect() {
        final String[] emptyInputs = {"", "  ", "\n  \n"};
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
        final String[] inputs = {"delete", "delete "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"delete notANumber ", "delete 8*wh12", "delete 1 2 3 4 5"};
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
        final String[] inputs = {"viewall", "viewall "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"viewall notAnumber ", "viewall 8*wh12", "viewall 1 2 3 4 5"};
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
        final String[] inputs = {"find", "find "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "find " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
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
        final String[] inputs = {"addPlayer", "addPlayer ", "addPlayer wrong args format",
                // no position prefix
                String.format("addPlayer %1$s %2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                // no age prefix
                String.format("addPlayer %1$s p/%2$s %3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                // no salary prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s %4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no GoalsScored prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s %5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no GoalsAssisted prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no TeamName prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s %7$s ctry/%8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no Nationality prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s %8$s jn/%9$s app/%10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no JerseyNumber prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s %9$s app/%10$s hs/%11$s", Name.EXAMPLE,
                        PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no Appearance prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s %10$s hs/%11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),

                //no HealthStatus prefix
                String.format("addPlayer %1$s p/%2$s a/%3$s sal/%4$s gs/%5$s "
                                + "ga/%6$s tm/%7$s ctry/%8$s jn/%9$s app/%10$s %11$s",
                        Name.EXAMPLE, PositionPlayed.EXAMPLE, Age.EXAMPLE, Salary.EXAMPLE, GoalsScored.EXAMPLE,
                        GoalsAssisted.EXAMPLE, TeamName.EXAMPLE, Nationality.EXAMPLE,
                        JerseyNumber.EXAMPLE, Appearance.EXAMPLE, HealthStatus.EXAMPLE),
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addCommand_invalidPlayerDataInArgs() {
        // name, age, salary, gs, ga, jn and appearance are the ones that need to be tested
        final String invalidName = "[]\\[;]";
        final String validName = Name.EXAMPLE;
        final String invalidAgeArg = "a/not_numbers";
        final String validAgeArg = "a/" + Age.EXAMPLE;
        final String invalidSalaryArg = "sal/not_number";
        final String validSalaryArg = "sal/" + Salary.EXAMPLE;
        final String invalidGsArg = "gs/not_number";
        final String validGsArg = "gs/" + GoalsScored.EXAMPLE;
        final String invalidGaArg = "ga/not_number";
        final String validGaArg = "ga/" + GoalsAssisted.EXAMPLE;
        final String invalidJnArg = "jn/not_number";
        final String validJnArg = "jn/" + JerseyNumber.EXAMPLE;
        final String invalidAppearanceArg = "app/not_number";
        final String validAppearanceArg = "app/" + Appearance.EXAMPLE;

        final String invalidTagArg = "t/invalid_-[.tag";

        // PositionPlayer, TeamName, Nationality and HealthStatus can be any string, so no invalid address
        // name, age, salary, gs, ga, jn, app

        final String addCommandFormatString = "addPlayer %1$s " + "p/"
                + PositionPlayed.EXAMPLE + " %2$s %3$s %4$s %5$s " + "tm/"
                + TeamName.EXAMPLE + " ctry/" + Nationality.EXAMPLE + " %6$s %7$s " + "hs/"
                + HealthStatus.EXAMPLE + " ";

        // test each incorrect player data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validAgeArg, validSalaryArg, validGsArg,
                        validGaArg, validJnArg, validAppearanceArg),
                // invalid age
                String.format(addCommandFormatString, validName, invalidAgeArg, validSalaryArg, validGsArg,
                        validGaArg, validJnArg, validAppearanceArg),
                // invalid salary
                String.format(addCommandFormatString, validName, validAgeArg, invalidSalaryArg, validGsArg,
                        validGaArg, validJnArg, validAppearanceArg),
                // invalid GS
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg, invalidGsArg,
                        validGaArg, validJnArg, validAppearanceArg),
                // invalid GA
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg, validGsArg,
                        invalidGaArg, validJnArg, validAppearanceArg),
                // invalid JN
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg, validGsArg,
                        validGaArg, invalidJnArg, validAppearanceArg),
                // invalid Appearance
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg, validGsArg,
                        validGaArg, validJnArg, invalidAppearanceArg),
                // invalid tag
                String.format(addCommandFormatString, validName, validAgeArg, validSalaryArg, validGsArg,
                        validGaArg, validJnArg, validAppearanceArg) + " " + invalidTagArg
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
                    new TeamName(TeamName.EXAMPLE),
                    new Nationality(Nationality.EXAMPLE),
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
                + " p/" + player.getPositionPlayed().fullPosition
                + " a/" + player.getAge().value
                + " sal/" + player.getSalary().value
                + " gs/" + player.getGoalsScored().value
                + " ga/" + player.getGoalsAssisted().value
                + " tm/" + player.getTeamName().fullTeam
                + " ctry/" + player.getNationality().fullCountry
                + " jn/" + player.getJerseyNumber().value
                + " app/" + player.getAppearance().value
                + " hs/" + player.getHealthStatus().fullHs;
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
     * @param input                to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */

    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }


}
