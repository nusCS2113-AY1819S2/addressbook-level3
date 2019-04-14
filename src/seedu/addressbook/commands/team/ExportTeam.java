package seedu.addressbook.commands.team;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.export.TeamApachePoiWriter;

/**
 * This command allows user to export team profiles in the league to an excel file
 */
public class ExportTeam extends Command {

    public static final String COMMAND_WORD = "exportTeam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "This Command will export all team profiles in the league tracker to an Excel sheet \n"
            + "For now, the output path has been hard-coded to be 'exported_team_record.xls'. \n"
            + "Every exportTeam Command will overwrite the excel file if it already exists \n";

    public static final String MESSAGE_SUCCESS = "Team profile is successfully exported";
    public static final String MESSAGE_NPE = "Please check your parameters... Object does not exist";


    public ExportTeam() {
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyTeam> allTeams = addressBook.getAllTeams().immutableListView();
        TeamApachePoiWriter writer = new TeamApachePoiWriter(allTeams);

        try {
            writer.write();
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_NPE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
