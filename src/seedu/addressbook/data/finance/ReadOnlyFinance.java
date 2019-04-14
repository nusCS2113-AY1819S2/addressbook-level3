package seedu.addressbook.data.finance;


/**
 * A read-only immutable interface for a finance in the league.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyFinance {

    String getTeamName();
    double getSponsor();
    double getTicketIncome();
    double getFinance();
    double getQuarterOne();
    double getQuarterTwo();
    double getQuarterThree();
    double getQuarterFour();
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
                && other.getTicketIncome() == this.getTicketIncome()
                && other.getFinance() == this.getFinance());
    }

    default String getAsTextShowSome() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName().trim())
                .append(" | Finance: USD");
        builder.append(getFinance());
        return builder.toString();

    }

    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName().trim())
                .append(" | Finance: USD");
        builder.append(getFinance())
                .append(" | Sponsorship: USD ");
        builder.append(getSponsor())
                .append(" |Ticket Income: USD");
        builder.append(getTicketIncome())
                .append(" |1st quarter: ");
        builder.append(getQuarterOne())
                .append(" |2nd quarter: ");
        builder.append(getQuarterTwo())
                .append(" |3rd quarter: ");
        builder.append(getQuarterThree())
                .append(" |4th quarter: ");
        builder.append(getQuarterFour())
                .append("\n Histogram: ")
                .append("\n");
        builder.append(getHistogramString());
        return builder.toString();
    }
}
