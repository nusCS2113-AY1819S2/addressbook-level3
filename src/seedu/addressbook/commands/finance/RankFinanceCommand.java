package seedu.addressbook.commands.finance;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;



/**
 * Gives a ranked list of all finances.
 */
public class RankFinanceCommand extends Command {

    public static final String COMMAND_WORD = "rankfinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays a ranked list of all Finances in the league tracker with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_FINANCE_REFRESH_PROBLEM =
            "invalid command due to finance refresh problem: duplicated team";


    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
            addressBook.sortFinance();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe) {
            return new CommandResult(MESSAGE_FINANCE_REFRESH_PROBLEM);
        }
        List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
        return new CommandResult(getMessageForFinanceListShownSummary(allFinances), null, null, null, allFinances);
    }
}
