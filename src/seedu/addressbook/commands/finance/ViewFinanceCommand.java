package seedu.addressbook.commands.finance;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.finance.ReadOnlyFinance;

/**
 * Shows all details of the finance identified using the last displayed index from the finance list.
 */

public class ViewFinanceCommand extends Command {

    public static final String COMMAND_WORD = "viewfinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the finance "
            + "identified by the index number in the last shown finance listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_VIEW_FINANCE_DETAILS = "Viewing Finance of Team: %1$s";


    public ViewFinanceCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyFinance target = getTargetFinance();
            if (!addressBook.containsFinance(target)) {
                return new CommandResult(Messages.MESSAGE_FINANCE_NOT_IN_LEAGUE_TRACKER);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_FINANCE_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_FINANCE_DISPLAYED_INDEX);
        }
    }
}
