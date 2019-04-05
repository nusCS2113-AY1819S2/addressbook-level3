package seedu.addressbook.commands.finance;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.finance.Histogram;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;

/**
 * Give total finance of the league.
 */
public class GetLeagueFinanceCommand extends Command {

    public static final String COMMAND_WORD = "getlf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Get total Finance of the league.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_FINANCE_REFRESH_PROBLEM =
            "invalid command due to finance refresh problem: duplicated team";

    public static final int INT_OF_QUARTER_ONE = 1;
    public static final int INT_OF_QUARTER_TWO = 2;
    public static final int INT_OF_QUARTER_THREE = 3;
    public static final int INT_OF_QUARTER_FOUR = 4;

    public static final String MESSAGE_SUCCESS = "Finance of the league: ";
    public static final String MESSAGE_SUCCESS_ONE = "1st quarter finance: ";
    public static final String MESSAGE_SUCCESS_TWO = "2nd quarter finance: ";
    public static final String MESSAGE_SUCCESS_THREE = "3rd quarter finance: ";
    public static final String MESSAGE_SUCCESS_FOUR = "4th quarter finance: ";
    public static final String MESSAGE_SUCCESS_FIVE = "Histogram of four quarters: ";


    @Override
    public CommandResult execute() {
        try {
            addressBook.refreshFinance();
        } catch (UniqueFinanceList.DuplicateFinanceException dfe) {
            return new CommandResult(MESSAGE_FINANCE_REFRESH_PROBLEM);
        }
        List<ReadOnlyFinance> allFinances = addressBook.getAllFinances().immutableListView();
        double totalFinance = getFinanceSum(allFinances);
        double totalFinanceQuarterOne = getFinancesWithCertainQuarter(allFinances, INT_OF_QUARTER_ONE);
        double totalFinanceQuarterTwo = getFinancesWithCertainQuarter(allFinances, INT_OF_QUARTER_TWO);
        double totalFinanceQuarterThree = getFinancesWithCertainQuarter(allFinances, INT_OF_QUARTER_THREE);
        double totalFinanceQuarterFour = getFinancesWithCertainQuarter(allFinances, INT_OF_QUARTER_FOUR);
        Histogram leagueHistogram = new Histogram(4,
                totalFinanceQuarterOne, totalFinanceQuarterTwo, totalFinanceQuarterThree, totalFinanceQuarterFour);
        String histogramString = leagueHistogram.getHistogramString();

        return new CommandResult(MESSAGE_SUCCESS + totalFinance + "\n"
                + MESSAGE_SUCCESS_ONE + totalFinanceQuarterOne + "\n"
                + MESSAGE_SUCCESS_TWO + totalFinanceQuarterTwo + "\n"
                + MESSAGE_SUCCESS_THREE + totalFinanceQuarterThree + "\n"
                + MESSAGE_SUCCESS_FOUR + totalFinanceQuarterFour + "\n\n"
                + MESSAGE_SUCCESS_FIVE + "\n" + histogramString);
    }

    /**
     * returns value of finance with a finance list.
     *
     * @param allFinances
     * @return sum of finance of allFinances
     */
    private double getFinanceSum(List<ReadOnlyFinance> allFinances) {
        double sumOfAllFinances = 0;
        for (ReadOnlyFinance finance : allFinances) {
            sumOfAllFinances += finance.getFinance();
        }
        return sumOfAllFinances;
    }

    /**
     * returns value of finance related to a certain time period.
     *
     * @param whichQuarter(relevant quarter of the year) for searching
     * @return value of finance within this quarter
     */
    private double getFinancesWithCertainQuarter(List<ReadOnlyFinance> relatedFinances, int whichQuarter) {
        double certainQuarterFinance = 0;
        for (ReadOnlyFinance finance : relatedFinances) {
            if (whichQuarter == INT_OF_QUARTER_ONE) {
                certainQuarterFinance += finance.getQuarterOne();
            }
            else if (whichQuarter == 2) {
                certainQuarterFinance += finance.getQuarterTwo();
            }
            else if (whichQuarter == 3) {
                certainQuarterFinance += finance.getQuarterThree();
            }
            else if (whichQuarter == 4) {
                certainQuarterFinance += finance.getQuarterFour();
            }
        }
        return certainQuarterFinance;
    }
}
