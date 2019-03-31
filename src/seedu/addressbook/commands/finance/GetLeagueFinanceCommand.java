package seedu.addressbook.commands.finance;

import java.util.*;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;

public class GetLeagueFinanceCommand extends Command {

    public static final String COMMAND_WORD = "getLeagueFinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Get total Finance of the league.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_FINANCE_REFRESH_PROBLEM = "invalid command due to finance refresh problem";

    public static final String MESSAGE_SUCCESS = "Finance of the league: ";


    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe) {
            return new CommandResult(MESSAGE_FINANCE_REFRESH_PROBLEM);
        }
        List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
        double totalFinance = getFinanceSum(allFinances);
        return new CommandResult(MESSAGE_SUCCESS + totalFinance);
    }

    private double getFinanceSum(List<ReadOnlyFinance> allFinances) {
        double sumOfAllFinances = 0;
        for (ReadOnlyFinance finance : allFinances) {
            sumOfAllFinances += finance.getFinance();
        }
        return sumOfAllFinances;
    }
}
