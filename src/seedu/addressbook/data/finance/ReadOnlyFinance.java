package seedu.addressbook.data.finance;


import java.util.Set;

/**
 * A read-only immutable interface for a finance in the league.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyFinance {

    String getTeamName();
    double getSponsor();
    double getVenueCost();
    double getTicketIncome();
    double getFinance();
    String getHistogramString();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyFinance other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTeamName().equals(this.getTeamName()) // state checks here onwards
                && other.getSponsor() == this.getSponsor()
                && other.getVenueCost() == this.getVenueCost()
                && other.getTicketIncome() == this.getTicketIncome()
                && other.getFinance() == this.getFinance());
    }


}
