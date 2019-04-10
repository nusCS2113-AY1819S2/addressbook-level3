package seedu.addressbook.commands.match;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.export.MatchApachePoiWriter;

/**
 * This Command allows user to export the match profiles in the league record to an Excel file
 */

public class ExportMatchCommand extends Command {

    public static final String COMMAND_WORD = "exportMatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "This Command will export all match information in the league tracker to an Excel sheet \n"
            + "For now, the output path has been hard-coded to be 'exported_match_record.xls'. \n"
            + "Every exportMatch Command will overwrite the excel file if it already exists \n";

    public static final String MESSAGE_SUCCESS = "All match information stored in League "
            + "Tracker has been successfully exported";
    public static final String MESSAGE_NPE = "Please check your parameters... Object does not exist";

    public ExportMatchCommand() {
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyMatch> allMatches = addressBook.getAllMatches().immutableListView();
        MatchApachePoiWriter writer = new MatchApachePoiWriter(allMatches);

        try {
            writer.write();
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_NPE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
