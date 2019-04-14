package seedu.addressbook.data.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * Represents Financial condition for a team in the League.
 */
public class Finance implements ReadOnlyFinance {

    public static final int NUMBER_OF_QUARTER = 4;
    public static final Set<String> QUARTER_ONE_MONTHS = Set.of("Jan", "Feb", "Mar");
    public static final Set<String> QUARTER_TWO_MONTHS = Set.of("Apr", "May", "Jun");
    public static final Set<String> QUARTER_THREE_MONTHS = Set.of("Jul", "Aug", "Sep");
    public static final Set<String> QUARTER_FOUR_MONTHS = Set.of("Oct", "Nov", "Dec");

    private String teamName;
    private double sponsorMoney = 0;
    private double ticketIncome = 0;

    private double quarterOne = 0;
    private double quarterTwo = 0;
    private double quarterThree = 0;
    private double quarterFour = 0;

    private Histogram histogram;


    public Finance(ReadOnlyTeam team) {

        this.teamName = team.getTeamName().toString();

        this.sponsorMoney = Double.valueOf(team.getSponsor().value);

        List<Match> matchesOfTeam = team.getMatches(); //gets relevant matches of the target team
        this.ticketIncome = getTicketIncomeFromMatches(matchesOfTeam, teamName);

        /**
         * gets each quarter's income with sponsorMoney/4 + ticket sale within each quarter.
         */
        this.quarterOne = sponsorMoney / 4
                + getTicketIncomeFromMatches(getMatchesWithMonths(matchesOfTeam, QUARTER_ONE_MONTHS), teamName);
        this.quarterTwo = sponsorMoney / 4
                + getTicketIncomeFromMatches(getMatchesWithMonths(matchesOfTeam, QUARTER_TWO_MONTHS), teamName);
        this.quarterThree = sponsorMoney / 4
                + getTicketIncomeFromMatches(getMatchesWithMonths(matchesOfTeam, QUARTER_THREE_MONTHS), teamName);
        this.quarterFour = sponsorMoney / 4
                + getTicketIncomeFromMatches(getMatchesWithMonths(matchesOfTeam, QUARTER_FOUR_MONTHS), teamName);

        this.histogram = new Histogram(NUMBER_OF_QUARTER, quarterOne, quarterTwo, quarterThree, quarterFour);
    }

    public Finance(String teamName,
                   double sponsorMoney,
                   double ticketIncome,
                   double quarterOne,
                   double quarterTwo,
                   double quarterThree,
                   double quarterFour) {
        this.teamName = teamName;
        this.sponsorMoney = sponsorMoney;
        this.ticketIncome = ticketIncome;
        this.quarterOne = quarterOne;
        this.quarterTwo = quarterTwo;
        this.quarterThree = quarterThree;
        this.quarterFour = quarterFour;
    }

    /**
     * calculates ticketIncome from relevant matches of the team.
     *
     * @param teamName for finding relevant homeSale/awaySale in each match
     * @return value of ticketSale
     */
    private double getTicketIncomeFromMatches (List<Match> relatedMatches, String teamName) {
        double ticketSale = 0;
        for (Match match : relatedMatches) {
            if (teamName.equals(match.getHome().toString()) && !match.getHomeSales().value.equals("")) {
                String homeSalesString = match.getHomeSales().value;
                double homeSalesValue = Double.valueOf(homeSalesString);
                ticketSale += homeSalesValue;
            } else if (teamName.equals(match.getAway().toString()) && !match.getAwaySales().value.equals("")) {
                String awaySalesString = match.getAwaySales().value;
                double awaySalesValue = Double.valueOf(awaySalesString);
                ticketSale += awaySalesValue;
            }
        }
        return ticketSale;
    }

    /**
     * returns all matches related to a certain time period.
     *
     * @param keywords (relevant months) for searching
     * @return set of matches found
     */
    private List<Match> getMatchesWithMonths(List<Match> relatedMatches, Set<String> keywords) {
        final List<Match> matchedMatches = new ArrayList<>();
        for (Match match : relatedMatches) {
            final Set<String> wordsInDate = new HashSet<>(match.getDate().getWordsInDate());
            if (!Collections.disjoint(wordsInDate, keywords)) {
                matchedMatches.add(match);
            }
        }
        return matchedMatches;
    }

    @Override
    public double getFinance() {
        double money;
        money = sponsorMoney + ticketIncome;
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
