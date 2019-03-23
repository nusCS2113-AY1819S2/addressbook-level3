package seedu.addressbook.data.finance;

import java.util.*;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Name;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.Sponsor;
import seedu.addressbook.data.match.ReadOnlyMatch;


/**
 * Represents Financial condition for a team in the League.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Finance implements ReadOnlyFinance {

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

    public static final int NUMBER_OF_QUARTER = 4;
    

    public Finance(ReadOnlyTeam team) {

        Name name = team.getName();
        teamName = name.fullName;

        Sponsor sponsor = team.getSponsor();
        String sponsorString = sponsor.value;
        this.sponsorMoney = Double.valueOf(sponsorString);

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


        /**
         *
         * wait for variables from other class
         *
         *Sponsor sponsorSponsor = team.getSponsor();
          *String venueCostString = sponsorSponsor.value;
         *this.venueCost = Double.valueOf(venueCostString);
         *
         * Sponsor sponsorSponsor = team.getSponsor();
         * String playerSalaryString = sponsorSponsor.value;
         * this.playerSalary = Double.valueOf(playerSalaryString);
         */
    }

    /**
     * Retrieve all matches in the address book whose homes is the target team's home.
     */
/**
    private List<ReadOnlyMatch> getMatchesWithHome(String home) {
        final List<ReadOnlyMatch> matchedMatches = new ArrayList<>();
        for (ReadOnlyMatch match : addressBook.getAllMatches()) {
            final String wordsInHome = match.getHome().toString();
            if (wordsInHome.equals(home)) {
                matchedMatches.add(match);
            }
        }
        return matchedMatches;
    }
*/

    @Override
    public double getFinance() {
        double money;
        money = sponsorMoney - venueCost + ticketIncome;
        return money;
    }

    @Override
    public String getTeamName() { return teamName; }

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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyFinance // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyFinance) other));
    }

    @Override
    public String getHistogramString(){
        return histogram.getHistogramString();
    }

}
