package seedu.addressbook.commands.player;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.export.PlayerApachePoiWriter;

/**
 * This Command allows user to export the player profiles in the league record to an Excel file
 */

public class ExportPlayerCommand extends Command {

    public static final String COMMAND_WORD = "exportPlayer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "This Command will export all player profiles in the league tracker to an Excel sheet \n"
            + "For now, the output path has been hard-coded to be 'exported_player_record.xls'. \n"
            + "Every exportPlayer Command will overwrite the excel file if it exists \n";

    public static final String MESSAGE_SUCCESS = "Player profile is successfully exported";
    //public static final String MESSAGE_IOE = "Error creating file...";
    //public static final String MESSAGE_FFE = "File not found...";
    public static final String MESSAGE_NPE = "Please check your parameters... Object does not exist";

    public ExportPlayerCommand() {
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPlayer> allPlayers = addressBook.getAllPlayers().immutableListView();
        PlayerApachePoiWriter writer = new PlayerApachePoiWriter(allPlayers);

        try {
            writer.write();
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_NPE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
