package seedu.addressbook.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DataAnalysisCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.finance.ExportFinanceCommand;
import seedu.addressbook.commands.finance.GetFinanceCommand;
import seedu.addressbook.commands.finance.GetLeagueFinanceCommand;
import seedu.addressbook.commands.finance.ListFinanceCommand;
import seedu.addressbook.commands.finance.RankFinanceCommand;
import seedu.addressbook.commands.finance.ViewFinanceCommand;
import seedu.addressbook.commands.match.AddMatchCommand;
import seedu.addressbook.commands.match.ClearMatchCommand;
import seedu.addressbook.commands.match.DeleteMatchCommand;
import seedu.addressbook.commands.match.ExportMatchCommand;
import seedu.addressbook.commands.match.FindMatchCommand;
import seedu.addressbook.commands.match.ListMatchCommand;
import seedu.addressbook.commands.match.UpdateMatchCommand;
import seedu.addressbook.commands.match.ViewMatchCommand;
import seedu.addressbook.commands.player.AddCommand;
import seedu.addressbook.commands.player.ClearCommand;
import seedu.addressbook.commands.player.DeleteCommand;
import seedu.addressbook.commands.player.EditPlayerCommand;
import seedu.addressbook.commands.player.ExportPlayerCommand;
import seedu.addressbook.commands.player.FindCommand;
import seedu.addressbook.commands.player.ListCommand;
import seedu.addressbook.commands.player.SortCommand;
import seedu.addressbook.commands.player.TransferPlayerCommand;
import seedu.addressbook.commands.player.ViewAllCommand;
import seedu.addressbook.commands.team.AddTeam;
import seedu.addressbook.commands.team.ClearTeam;
import seedu.addressbook.commands.team.DeleteTeam;
import seedu.addressbook.commands.team.EditTeam;
import seedu.addressbook.commands.team.ExportTeam;
import seedu.addressbook.commands.team.FindTeam;
import seedu.addressbook.commands.team.ListTeam;
import seedu.addressbook.commands.team.ViewTeam;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.MatchDate;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.Score;
import seedu.addressbook.data.match.TicketSales;
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
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.team.Country;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.TeamName;

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
    * transferPlayer Command testing
    */
    @Test
    public void transferCommand_parsedCorrectly() {
        final String input = "transfer Lionel Messi tm/A jn/10 sal/200";
        final TransferPlayerCommand result = parseAndAssertCommandType(input, TransferPlayerCommand.class);
        assertEquals(result.getClass(), TransferPlayerCommand.class);
    }

    @Test
    public void transferCommand_noArgs() {
        final String[] inputs = {"transfer ", "transfer "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TransferPlayerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void transferCommand_invalidArgs_wrongPrefix() {
        final String[] inputs = {"transfer wrong args format",
                // no team name prefix
                String.format("transfer Messi %1$s jn/20 sal/100", TeamName.EXAMPLE),
                // no jersey number prefix
                String.format("transfer Messi tm/FC Barcelona %1$s sal/100", JerseyNumber.EXAMPLE),
                // no salary prefix
                String.format("transfer Messi tm/FC Barcelona jn/10 %1$s", Salary.EXAMPLE)
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TransferPlayerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    /**
     * editPlayer Command testing
     */

    @Test
    public void editPlayerCommand_validEditData_parsedCorrectly() {
        final String input = "editPlayer 1 p/CB";
        final EditPlayerCommand result = parseAndAssertCommandType(input, EditPlayerCommand.class);
        assertEquals(result.getClass(), EditPlayerCommand.class);
    }

    @Test
    public void editPlayer_noArgs() {
        final String[] inputs = {"editPlayer ", "editPlayer"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditPlayerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void editPlayerCommand_invalidArgs_wrongPrefix() {
        final String[] inputs = {"editPlayer", "editPlayer ", "editPlayer wrong args format",

                // wrong position prefix
                String.format("editPlayer 1 wp/%1$s", PositionPlayed.EXAMPLE),

                // wrong age prefix
                String.format("editPlayer 1 wa/%1$s", Age.EXAMPLE),

                // wrong salary prefix
                String.format("editPlayer 1 wsal/%1$s", Salary.EXAMPLE),

                // wrong GoalsScored prefix
                String.format("editPlayer 1 wgs/%1$s", GoalsScored.EXAMPLE),

                // wrong GoalsAssisted prefix
                String.format("editPlayer 1 wga/%1$s", GoalsAssisted.EXAMPLE),

                // wrong TeamName prefix
                String.format("editPlayer 1 wtm/%1$s", TeamName.EXAMPLE),

                // wrong Nationality prefix
                String.format("editPlayer 1 wctry/%1$s", Nationality.EXAMPLE),

                // wrong JerseyNumber prefix
                String.format("editPlayer 1 wjn/%1$s", JerseyNumber.EXAMPLE),

                // wrong Appearance prefix
                String.format("editPlayer 1 wapp/%1$s", Appearance.EXAMPLE),

                // wrong HealthStatus prefix
                String.format("editPlayer 1 whs/%1$s", HealthStatus.EXAMPLE),
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPlayerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
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
                + " tm/" + player.getTeamName().fullName
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
     * Test for export commands
     */

    @Test
    public void exportPlayerCommand_parsedCorrectly() {
        final String input = "exportPlayer";
        parseAndAssertCommandType(input, ExportPlayerCommand.class);
    }

    @Test
    public void exportTeamCommand_parsedCorrectly() {
        final String input = "exportTeam";
        parseAndAssertCommandType(input, ExportTeam.class);
    }

    @Test
    public void exportMatchCommand_parsedCorrectly() {
        final String input = "exportMatch";
        parseAndAssertCommandType(input, ExportMatchCommand.class);
    }

    @Test
    public void exportFinanceCommand_parsedCorrectly() {
        final String input = "exportFinance";
        parseAndAssertCommandType(input, ExportFinanceCommand.class);
    }

    /**
     * Test for data analysis
     */

    @Test
    public void dataAnalysisCommand_parsedCorrectly() {
        final String input = "generateReport";
        parseAndAssertCommandType(input, DataAnalysisCommand.class);
    }


    /**
     * Test for team management begin here
     */

    @Test
    public void clearTeamCommand_parsedCorrectly() {
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
     * Generates a test team
     */
    private static Team generateTestTeam() {
        try {
            return new Team(
                    new seedu.addressbook.data.team.TeamName(TeamName.EXAMPLE),
                    new Country(Country.EXAMPLE),
                    new Sponsor(Sponsor.EXAMPLE),
                    new ArrayList<>(),
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
     * Test for match commands start here
     *
     * Test 0-argument match commands
     */

    @Test
    public void clearMatchCommand_parsedCorrectly() {
        final String input = "clearmatch";
        parseAndAssertCommandType(input, ClearMatchCommand.class);
    }

    @Test
    public void listMatchCommand_parsedCorrectly() {
        final String input = "listmatch";
        parseAndAssertCommandType(input, ListMatchCommand.class);
    }

    /**
     * Test Single index argument match commands
     */

    @Test
    public void deleteMatchCommand_noArgs() {
        final String[] inputs = {"deletematch", "deletematch "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteMatchCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"deletematch alphabets ", "deletematch 2p2", "deletematch 1 2 3 4 5"};
        final String resultMessage;
        resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteMatchCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "deletematch " + testIndex;
        final DeleteMatchCommand result = parseAndAssertCommandType(input, DeleteMatchCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewMatchCommand_noArgs() {
        final String[] inputs = { "viewmatch", "viewmatch " };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewMatchCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewmatch notAnumber ", "viewmatch 8*wh12", "viewmatch 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewMatchCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewmatch " + testIndex;
        final ViewMatchCommand result = parseAndAssertCommandType(input, ViewMatchCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test find matches by keyword in date command
     */

    @Test
    public void findMatchCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {"findmatch", "findmatch "};
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findMatchCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "13", "Jan", "1991" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));
        final String input = "findmatch " + String.join(" ", keySet);
        final FindMatchCommand result =
                parseAndAssertCommandType(input, FindMatchCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findMatchCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "13", "Jan", "1991" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "findmatch " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindMatchCommand result =
                parseAndAssertCommandType(input, FindMatchCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add match command
     */

    @Test
    public void addMatchCommand_invalidArgs() {
        final String[] inputs = {"addmatch", "addmatch ", "addmatch wrong args format",
                // no home prefix
                String.format("addmatch %1$s %2$s a/%3$s",
                        MatchDate.EXAMPLE, "Chelsea", "Everton"),

                // no away prefix
                String.format("addmatch %1$s h/%2$s %3$s",
                        MatchDate.EXAMPLE, "Chelsea", "Everton"),
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addMatchCommand_invalidMatchDataInArgs() {
        // matchdate, home and away are the ones that need to be tested
        final String invalidMatchDate = "32 Feb 01";
        final String validMatchDate = MatchDate.EXAMPLE;
        final String invalidHomeArg = "h/has_underscore";
        final String validHomeArg = "h/Chelsea";
        final String invalidAwayArg = "a/contains_underscore";
        final String validAwayArg = "a/Everton";

        final String addMatchFormatString = "addmatch %1$s %2$s %3$s ";

        // test each incorrect match data field argument individually
        final String[] inputs = {
                // invalid date
                String.format(addMatchFormatString, invalidMatchDate, validHomeArg, validAwayArg),
                // invalid home
                String.format(addMatchFormatString, validMatchDate, invalidHomeArg, validAwayArg),
                // invalid away
                String.format(addMatchFormatString, validMatchDate, validHomeArg, invalidAwayArg),
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addMatchCommand_validMatchData_parsedCorrectly() {
        final Match testMatch = generateTestMatch();
        final String input = convertMatchToAddMatchString(testMatch);
        final AddMatchCommand result = parseAndAssertCommandType(input, AddMatchCommand.class);
        assertEquals(result.getMatch(), testMatch);
    }


    @Test
    public void updateMatchCommand_noArgs() {
        final String[] inputs = {"updatematch", "updatematch "};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void updateMatchCommand_argsIsNotSingleNumber() {
        final String[] inputs = {"updatematch notAnumber ", "updatematch 8*wh12", "updatematch 1 2 3 4 5"};
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateMatchCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void updateMatchCommand_invalidMatchDataInArgs() {
        // homeSales, awaySales, goalScorers and ownGoalScorers are the ones that need to be tested
        final String invalidHomeSales = "h/should_be_numbers";
        final String validHomeSales = "h/" + TicketSales.EXAMPLE;
        final String invalidAwaySales = "a/should_be_numbers";
        final String validAwaySales = "a/" + TicketSales.EXAMPLE;
        final String invalidGoalScorersArg = "g/contain_underscore";
        final String validGoalScorersArg = "g/" + Name.EXAMPLE;
        final String invalidOwnGoalScorersArg = "o/contain_underscore";
        final String validOwnGoalScorersArg = "o/" + Name.EXAMPLE;

        final String updateMatchFormatString = "updatematch 1 %1$s %2$s %3$s %4$s";

        // test each incorrect match data field argument individually
        final String[] inputs = {
                // invalid homesales
                String.format(updateMatchFormatString, invalidHomeSales, validAwaySales,
                        validGoalScorersArg, validOwnGoalScorersArg),
                // invalid awaysales
                String.format(updateMatchFormatString, validHomeSales, invalidAwaySales,
                        validGoalScorersArg, validOwnGoalScorersArg),
                // invalid goalscorer
                String.format(updateMatchFormatString, validHomeSales, validAwaySales,
                        invalidGoalScorersArg, validOwnGoalScorersArg),
                // invalid owngoalscorer
                String.format(updateMatchFormatString, validHomeSales, validAwaySales,
                        validGoalScorersArg, invalidOwnGoalScorersArg)
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    /**
     * Generates a test match
     */
    private static Match generateTestMatch() {
        try {
            return new Match(
                    new MatchDate(MatchDate.EXAMPLE),
                    new TeamName("Chelsea"),
                    new TeamName("Everton"),
                    new TicketSales(""),
                    new TicketSales(""),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new Score("")
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test match data should be valid by definition");
        } catch (ParseException pe) {
            throw new RuntimeException(pe.getMessage());
        }
    }

    /**
     * Converts a match to addMatchCommand string.
     *
     * @param match
     * @return
     */
    private static String convertMatchToAddMatchString(ReadOnlyMatch match) {
        return "addmatch "
                + match.getDate().toString()
                + " h/" + match.getHome().fullName
                + " a/" + match.getAway().fullName;
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
