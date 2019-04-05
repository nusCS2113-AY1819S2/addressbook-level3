package seedu.addressbook.commands.finance;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.team.ReadOnlyTeam;


/**
 * check the financial profit in USD of a team identified using it's last displayed index from the League.
 */

public class GetFinanceCommand extends Command {

    public static final String COMMAND_WORD = "getfinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Checks the financial condition of a team identified using its last displayed index from the League.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS_ONE = "Team selected: ";
    public static final String MESSAGE_SUCCESS_TWO = "Finance of the team selected: ";
    public static final String MESSAGE_SUCCESS_THREE = "1st quarter finance: ";
    public static final String MESSAGE_SUCCESS_FOUR = "2nd quarter finance: ";
    public static final String MESSAGE_SUCCESS_FIVE = "3rd quarter finance: ";
    public static final String MESSAGE_SUCCESS_SIX = "4th quarter finance: ";
    public static final String MESSAGE_SUCCESS_SEVEN = "Histogram of four quarters: ";


    public GetFinanceCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTeam target = getTargetTeam();
            Finance finance = new Finance(target);
            String teamName = finance.getTeamName();
            String histogramString = finance.getHistogramString();
            return new CommandResult(MESSAGE_SUCCESS_ONE + teamName + "\n"
                    + MESSAGE_SUCCESS_TWO + finance.getFinance() + "\n"
                    + MESSAGE_SUCCESS_THREE + finance.getQuarterOne() + "\n"
                    + MESSAGE_SUCCESS_FOUR + finance.getQuarterTwo() + "\n"
                    + MESSAGE_SUCCESS_FIVE + finance.getQuarterThree() + "\n"
                    + MESSAGE_SUCCESS_SIX + finance.getQuarterFour() + "\n\n"
                    + MESSAGE_SUCCESS_SEVEN + "\n" + histogramString);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
    }

}
