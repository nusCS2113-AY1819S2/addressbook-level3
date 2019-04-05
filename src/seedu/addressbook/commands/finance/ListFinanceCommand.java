package seedu.addressbook.commands.finance;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;


/**
 * Lists all finances.
 */
public class ListFinanceCommand extends Command {

    public static final String COMMAND_WORD = "listfinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all Finances in the league tracker as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_FINANCE_REFRESH_PROBLEM =
            "invalid command due to finance refresh problem: duplicated team";


    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe) {
            return new CommandResult(MESSAGE_FINANCE_REFRESH_PROBLEM);
        }
        List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
        return new CommandResult(getMessageForFinanceListShownSummary(allFinances), null, null, null, allFinances);
    }
}
