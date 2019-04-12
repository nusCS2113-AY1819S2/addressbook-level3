package seedu.addressbook.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.finance.GetFinanceCommand;
import seedu.addressbook.commands.finance.GetLeagueFinanceCommand;
import seedu.addressbook.commands.finance.ListFinanceCommand;
import seedu.addressbook.commands.finance.RankFinanceCommand;
import seedu.addressbook.commands.finance.ViewFinanceCommand;
import seedu.addressbook.commands.player.AddCommand;
import seedu.addressbook.commands.player.ClearCommand;
import seedu.addressbook.commands.player.DeleteCommand;
import seedu.addressbook.commands.player.FindCommand;
import seedu.addressbook.commands.player.ListCommand;
import seedu.addressbook.commands.player.SortCommand;
import seedu.addressbook.commands.player.ViewAllCommand;
import seedu.addressbook.commands.team.AddTeam;
import seedu.addressbook.commands.team.ClearTeam;
import seedu.addressbook.commands.team.DeleteTeam;
import seedu.addressbook.commands.team.EditTeam;
import seedu.addressbook.commands.team.FindTeam;
import seedu.addressbook.commands.team.ListTeam;
import seedu.addressbook.commands.team.ViewTeam;
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
import seedu.addressbook.data.team.Country;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.team.Team;

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
    public void sortCommand_parsedCorrectly() {
        final String input = "sortPlayer";
        parseAndAssertCommandType(input, SortCommand.class);
    }

    @Test
    public void clearCommand_parsedCorrectly() {
        final String input = "clearPlayer";
        parseAndAssertCommandType(input, ClearCommand.class);
    }

    @Test
    public void listCommand_parsedCorrectly() {
        final String input = "listPlayer";
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
        final String[] inputs = {"deletePlayer", "deletePlayer "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"deletePlayer notANumber ", "deletePlayer 8*wh12", "deletePlayer 1 2 3 4 5"};
        final String resultMessage;
        resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "deletePlayer " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewAllCommand_noArgs() {
        final String[] inputs = {"displayProfile", "displayProfile "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"displayProfile notAnumber ", "displayProfile 8*wh12", "displayProfile 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }


    @Test
    public void viewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "displayProfile " + testIndex;
        final ViewAllCommand result = parseAndAssertCommandType(input, ViewAllCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }



    /**
     * Test find players by keyword in name command
     */

    @Test
    public void findCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {"findPlayer", "findPlayer "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "findPlayer " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "findPlayer " + String.join(" ", keySet) + " " + String.join(" ", keySet);
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
     * Test for team management begin here
     */

    @Test
    public void clearTeamCommand_parsedCorretly() {
        final String input = "clearteam";
        parseAndAssertCommandType(input, ClearTeam.class);
    }


    @Test
    public void listTeam_parsedCorrectly() {
        final String input = "listteam";
        parseAndAssertCommandType(input, ListTeam.class);
    }

    @Test
    public void deleteTeam_noArgs() {
        final String[] inputs = {"deleteteam", "deleteteam "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteTeam_argsIsNotSingleNumber() {
        final String[] inputs = {"deleteteam notANumber ", "deleteteam 8*wh12", "deleteteam 1 2 3 4 5"};
        final String resultMessage;
        resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteTeam_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "deleteteam " + testIndex;
        final DeleteTeam result = parseAndAssertCommandType(input, DeleteTeam.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewTeam_noArgs() {
        final String[] inputs = {"viewteam", "viewteam "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewTeam_argsIsNotSingleNumber() {
        final String[] inputs = {"viewteam notAnumber ", "viewteam 8*wh12", "viewteam 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewTeam_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewteam " + testIndex;
        final ViewTeam result = parseAndAssertCommandType(input, ViewTeam.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }


    /**
     * Test find teams by keyword in name command
     */

    @Test
    public void findTeam_invalidArgs() {
        // no keywords
        final String[] inputs = {"findteam", "findteam "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findTeam_validArgs_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "findteam " + String.join(" ", keySet);
        final FindTeam result =
                parseAndAssertCommandType(input, FindTeam.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findTeam_duplicateKeys_parsedCorrectly() {
        final String[] keywords = {"key1", "key2", "key3"};
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "findteam " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindTeam result =
                parseAndAssertCommandType(input, FindTeam.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add team command
     */
    @Test
    public void addTeam_invalidArgs() {
        final String[] inputs = {"addteam", "addteam ", "addteam wrong args format",
                // no country prefix
                String.format("addteam %1$s %2$s s/%3$s",
                        TeamName.EXAMPLE, Country.EXAMPLE, Sponsor.EXAMPLE),

                // no sponsor prefix
                String.format("addteam %1$s c/%2$s %3$s",
                        TeamName.EXAMPLE, Country.EXAMPLE, Sponsor.EXAMPLE),
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addTeam_invalidTeamDataInArgs() {
        // name, country and sponsor are the ones that need to be tested
        final String invalidTeamName = "[]\\[;]";
        final String validTeamName = TeamName.EXAMPLE;
        final String invalidCountryArg = "c/contain_numbers123";
        final String validCountryArg = "c/" + Country.EXAMPLE;
        final String invalidSponsorArg = "s/not_number";
        final String validSponsorArg = "s/" + Sponsor.EXAMPLE;

        final String addTeamFormatString = "addteam %1$s %2$s %3$s ";

        // test each incorrect team data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addTeamFormatString, invalidTeamName, validCountryArg, validSponsorArg),
                // invalid country
                String.format(addTeamFormatString, validTeamName, invalidCountryArg, validSponsorArg),
                // invalid sponsor
                String.format(addTeamFormatString, validTeamName, validCountryArg, invalidSponsorArg),
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addTeam_validTeamData_parsedCorrectly() {
        final Team testTeam = generateTestTeam();
        final String input = convertTeamToAddTeamString(testTeam);
        final AddTeam result = parseAndAssertCommandType(input, AddTeam.class);
        assertEquals(result.getTeam(), testTeam);
    }


    @Test
    public void addTeam_duplicateTags_merged() throws IllegalValueException {
        final Team testTeam = generateTestTeam();
        String input = convertTeamToAddTeamString(testTeam);
        for (Tag tag : testTeam.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final AddTeam result = parseAndAssertCommandType(input, AddTeam.class);
        assertEquals(result.getTeam(), testTeam);
    }

    /**
     * Test Edit command function
     */

    @Test
    public void editTeam_noArgs() {
        final String[] inputs = {"editteam", "editteam "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void editTeam_argsIsNotSingleNumber() {
        final String[] inputs = {"editteam notAnumber ", "editteam 8*wh12", "editteam 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTeam.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void editTeam_invalidTeamDataInArgs() {
        // name, country and sponsor are the ones that need to be tested
        final String invalidTeamName = "n/[]\\[;]";
        final String validTeamName = "n/" + TeamName.EXAMPLE;
        final String invalidCountryArg = "c/contain_numbers123";
        final String validCountryArg = "c/" + Country.EXAMPLE;
        final String invalidSponsorArg = "s/not_number";
        final String validSponsorArg = "s/" + Sponsor.EXAMPLE;

        final String addTeamFormatString = "editteam 1 %1$s %2$s %3$s ";

        // test each incorrect team data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addTeamFormatString, invalidTeamName, validCountryArg, validSponsorArg),
                // invalid country
                String.format(addTeamFormatString, validTeamName, invalidCountryArg, validSponsorArg),
                // invalid sponsor
                String.format(addTeamFormatString, validTeamName, validCountryArg, invalidSponsorArg),
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    /**
     * generates a test team
     */
    private static Team generateTestTeam() {
        try {
            return new Team(
                    new seedu.addressbook.data.team.TeamName(TeamName.EXAMPLE),
                    new Country(Country.EXAMPLE),
                    new Sponsor(Sponsor.EXAMPLE),
                    new HashSet<>(),
                    new ArrayList<>(),
                    new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test team data should be valid by definition");
        }
    }

    /**
     * Converts team to add command string
     */
    private static String convertTeamToAddTeamString(ReadOnlyTeam team) {
        String addTeam = "addteam "
                + team.getTeamName().fullName
                + " c/" + team.getCountry().value
                + " s/" + team.getSponsor().value;
        for (Tag tag : team.getTags()) {
            addTeam += " t/" + tag.tagName;
        }
        return addTeam;
    }

    /**
     * Converts team to add command string
     */
    private static String convertTeamToEditTeamString(ReadOnlyTeam team) {
        String editTeam = "editteam 1"
                + " n/" + team.getTeamName().fullName
                + " c/" + team.getCountry().value
                + " s/" + team.getSponsor().value;
        for (Tag tag : team.getTags()) {
            editTeam += " t/" + tag.tagName;
        }
        return editTeam;
    }


    /**
     * Test ListFinanceCommand
     */
    @Test
    public void listFinanceCommand_parsedCorrectly() {
        final String input = "listfinance";
        parseAndAssertCommandType(input, ListFinanceCommand.class);
    }

    /**
     * Test RankFinanceCommand
     */
    @Test
    public void rankFinanceCommand_parsedCorrectly() {
        final String input = "rankfinance";
        parseAndAssertCommandType(input, RankFinanceCommand.class);
    }

    /**
     * Test GetLeagueFinanceCommand
     */
    @Test
    public void getLeagueFinanceCommand_parsedCorrectly() {
        final String input = "getlf";
        parseAndAssertCommandType(input, GetLeagueFinanceCommand.class);
    }

    /**
     * Test ViewFinanceCommand with single index argument commands
     */
    @Test
    public void viewFinanceCommand_noArgs() {
        final String[] inputs = {"viewfinance", "viewfinance "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewFinanceCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewFinanceCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"viewfinance notAnumber ", "viewfinance 7random12", "viewfinance 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewFinanceCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewFinanceCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewfinance " + testIndex;
        final ViewFinanceCommand result = parseAndAssertCommandType(input, ViewFinanceCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test GetFinanceCommand with single index argument commands
     */
    @Test
    public void getFinanceCommand_noArgs() {
        final String[] inputs = {"getfinance", "getfinance "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetFinanceCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void getFinanceCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"getfinance notAnumber ", "getfinance 7random12", "getfinance 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetFinanceCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void getFinanceCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "getfinance " + testIndex;
        final GetFinanceCommand result = parseAndAssertCommandType(input, GetFinanceCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
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
