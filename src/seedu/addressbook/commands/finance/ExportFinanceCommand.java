package seedu.addressbook.commands.finance;


import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;
import seedu.addressbook.export.FinanceApachePoiWriter;

/**
 * This Command allows user to export finance details in the league tracker to an Excel file
 */
public class ExportFinanceCommand extends Command {

    public static final String COMMAND_WORD = "exportFinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "This Command will export all finance details in the league tracker to an Excel sheet \n"
            + "For now, the output path has been hard-coded to be 'exported_finance_record.xls'. \n"
            + "Every exportFinance Command will overwrite the excel file if it already exists \n";

    public static final String MESSAGE_SUCCESS = "Finance records are successfully exported";
    public static final String MESSAGE_NPE = "Please check your parameters... Object does not exist";
    public static final String MESSAGE_DFE = "Duplicated finance records created...";

    public ExportFinanceCommand () {
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
            List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
            FinanceApachePoiWriter writer = new FinanceApachePoiWriter(allFinances);

            writer.write();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe) {
            return new CommandResult(MESSAGE_DFE);
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_NPE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
