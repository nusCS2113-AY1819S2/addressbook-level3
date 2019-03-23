package seedu.addressbook.commands.finance;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;
import seedu.addressbook.data.team.ReadOnlyTeam;

import java.util.List;

/**
 * Lists all finances.
 */
public class ListFinanceCommand extends Command {

    public static final String COMMAND_WORD = "listFinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all Finances in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_FINANCE_REFRESH_PROBLEM = "invalid command due to finance refresh problem";


    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe){
            return new CommandResult(MESSAGE_FINANCE_REFRESH_PROBLEM);
        }
        List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
        return new CommandResult(getMessageForFinanceListShownSummary(allFinances), null, null, null, allFinances);
    }
}
