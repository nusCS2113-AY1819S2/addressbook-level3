package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * check the financial profit in USD of a team identified using it's last displayed index from the League.
 */

public class FinanceCommand extends Command {

    public static final String COMMAND_WORD = "finance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Checks the financial condition of a team identified using its last displayed index from the League.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Finance of the team selected:";


    public FinanceCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTeam target = getTargetTeam();
            Finance finance = new Finance(target);
            double money = finance.getFinance();
            return new CommandResult(MESSAGE_SUCCESS + " " + money);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
    }

}
