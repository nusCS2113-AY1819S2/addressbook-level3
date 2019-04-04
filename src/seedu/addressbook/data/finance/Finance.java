package seedu.addressbook.data.finance;

import seedu.addressbook.data.team.Name;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;


/**
 * Represents Financial condition for a team in the League.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Finance implements ReadOnlyFinance {

    public static final int NUMBER_OF_QUARTER = 4;

    private String teamName;
    private double sponsorMoney = 0;
    private double venueCost = 0;
    //private double playerSalary = 0;
    private double ticketIncome = 0;

    private double quarterOne = 0;
    private double quarterTwo = 0;
    private double quarterThree = 0;
    private double quarterFour = 0;

    private Histogram histogram;


    public Finance(ReadOnlyTeam team) {

        Name name = team.getName();
        this.teamName = name.fullName;

        Sponsor sponsor = team.getSponsor();
        String sponsorString = sponsor.value;
        this.sponsorMoney = Double.valueOf(sponsorString);

        this.quarterOne = sponsorMoney / 4;
        this.quarterTwo = sponsorMoney / 4;
        this.quarterThree = sponsorMoney / 4;
        this.quarterFour = sponsorMoney / 4;

        this.histogram = new Histogram(NUMBER_OF_QUARTER, quarterOne, quarterTwo, quarterThree, quarterFour);


        /**
         * Retrieve all matches in the address book whose homes is the target team's home.
         */

        //String homeString = teamName;
        //final List<ReadOnlyMatch> matchesFound = getMatchesWithHome(homeString);

        /**
         * Retrieve ticket price and turnout rate from all matches found.
         */
        /**
        for (ReadOnlyMatch match : matchesOfTeam) {
            double price = 1;//match.getPrice();
            double turnout = 2;//match.getTurnout();
            double ticketBox = price * turnout;
            ticketIncome += ticketBox;

        }
         */
    }

    @Override
    public double getFinance() {
        double money;
        money = sponsorMoney - venueCost + ticketIncome;
        return money;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public double getSponsor() {
        return sponsorMoney;
    }

    @Override
    public double getVenueCost() {
        return venueCost;
    }

    @Override
    public double getTicketIncome() {
        return ticketIncome;
    }

    @Override
    public double getQuarterOne() {
        return quarterOne;
    }

    @Override
    public double getQuarterTwo() {
        return quarterTwo;
    }

    @Override
    public double getQuarterThree() {
        return quarterThree;
    }

    @Override
    public double getQuarterFour() {
        return quarterFour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyFinance // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyFinance) other));
    }

    @Override
    public String getHistogramString() {
        return histogram.getHistogramString();
    }

}
