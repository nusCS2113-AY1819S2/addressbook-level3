package seedu.addressbook.commands;

import seedu.addressbook.commands.finance.GetFinanceCommand;
import seedu.addressbook.commands.finance.GetLeagueFinanceCommand;
import seedu.addressbook.commands.finance.ListFinanceCommand;
import seedu.addressbook.commands.finance.RankFinanceCommand;
import seedu.addressbook.commands.finance.ViewFinanceCommand;
import seedu.addressbook.commands.match.AddMatchCommand;
import seedu.addressbook.commands.match.ClearMatchCommand;
import seedu.addressbook.commands.match.DeleteMatchCommand;
import seedu.addressbook.commands.match.FindMatchCommand;
import seedu.addressbook.commands.match.ListMatchCommand;
import seedu.addressbook.commands.player.AddCommand;
import seedu.addressbook.commands.player.AddFastCommand;
import seedu.addressbook.commands.player.ClearCommand;
import seedu.addressbook.commands.player.DeleteCommand;
import seedu.addressbook.commands.player.EditPlayerCommand;
import seedu.addressbook.commands.player.ExportPlayerCommand;
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

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String PLAYER_COMMAND = "\n\nPLAYER COMMANDS:\n";
    public static final String TEAM_COMMAND = "\n\nTEAM COMMANDS:\n";
    public static final String MATCH_COMMAND = "\n\nMATCH COMMANDS:\n";
    public static final String FINANCE_COMMAND = "\n\nFINANCE COMMANDS:\n";
    public static final String COMMON_COMMAND = "COMMON COMMANDS:\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_ALL_USAGES = "Below is a help sheet for commands available in League Tracker.\n"
            + "================================================================================ \n" + "\n"
            + "1. Commands on Players in League Tracker \n" + "\n"
            + "1.1 Add a player to League Tracker \n"
            + AddCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.2 Add a player to League Tracker faster"
            + "\n" + AddFastCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.3 Edit a player profile in League Tracker"
            + "\n" + EditPlayerCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.4 Delete an existing player in League Tracker"
            + "\n" + DeleteCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.5 Delete all player profiles in League Tracker (CAREFUL)"
            + "\n" + ClearCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.6 Use keywords to search for players in the League Tracker"
            + "\n" + FindCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.7 List all players in League Tracker"
            + "\n" + ListCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.8 Sort players in League Tracker alphabetically"
            + "\n" + SortCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.9 Export player profile in League Tracker to an Excel file"
            + "\n" + ExportPlayerCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "1.10 View the player profile by index"
            + "\n" + ViewAllCommand.MESSAGE_USAGE
            + "================================================================================= \n" + "\n"
            + "2. Commands on Teams in League Tracker \n" + "\n"
            + "2.1 Add a team to League Tracker"
            + "\n" + AddTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.2 Edit a team profile in League Tracker"
            + "\n" + EditTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.3 Delete an existing team in League Tracker"
            + "\n" + DeleteTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.4 Delete all teams in League Tracker (CAREFUL)"
            + "\n" + ClearTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.5 Use keywords to search for teams in the League Tracker"
            + "\n" + FindTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.6 List all teams existing in League Tracker"
            + "\n" + ListTeam.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "2.7 View full profile of a team in League Tracker "
            + "\n" + ViewTeam.MESSAGE_USAGE + "\n"
            + "================================================================================= \n" + "\n"
            + "3. Commands on Finance in League Tracker \n" + "\n"
            + "3.1 Obtain the financial records of team by index"
            + "\n" + GetFinanceCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "3.2 Obtain the financial records of the whole league"
            + "\n" + GetLeagueFinanceCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "3.3 Rank the teams based on their financial conditions"
            + "\n" + RankFinanceCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "List all financial records of teams in the League Tracker"
            + "\n" + ListFinanceCommand.MESSAGE_USAGE + "\n"
            + "================================================================================= \n" + "\n"
            + "4. Commands on Finance in League Tracker \n" + "\n"
            + "4.1 Add a match to League Tracker"
            + "\n" + AddMatchCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "4.2 Delete an existing match from League Tracker"
            + "\n" + DeleteMatchCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "4.3 Delete all matches in League Tracker (CAREFUL)"
            + "\n" + ClearMatchCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "4.4 Use keywords to search for matches in League Tracker"
            + "\n" + FindMatchCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "4.5 List all matches in League Tracker"
            + "\n" + ListMatchCommand.MESSAGE_USAGE + "\n"
            + "================================================================================= \n" + "\n"
            + "5. General commands in League Tracker \n" + "\n"
            + "5.1 Display a help sheet"
            + "\n" + HelpCommand.MESSAGE_USAGE
            + "------------------------------------------------------------------------------------------------------ \n"
            + "5.2 Exit from League Tracker"
            + "\n" + ExitCommand.MESSAGE_USAGE;



    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
