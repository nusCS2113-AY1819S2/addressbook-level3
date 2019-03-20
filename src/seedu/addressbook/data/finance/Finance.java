package seedu.addressbook.data.finance;

import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Sponsor;

/**
 * Represents Financial condition for a team in the League.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Finance {

    private double sponsor = 0;
    private double venueCost = 0;
    private double playerSalary = 0;
    private double ticketIncome = 0;


    public Finance(ReadOnlyTeam team) {
        Sponsor sponsorSponsor = team.getSponsor();
        String sponsorString = sponsorSponsor.value;
        this.sponsor = Double.valueOf(sponsorString);
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
         *
         * Sponsor sponsorSponsor = team.getSponsor();
         * String sponsorString = sponsorSponsor.value;
         * this.sponsor = Double.valueOf(sponsorString);
         */
    }

    public double getFinance() {
        double money;
        money = sponsor - venueCost + playerSalary + ticketIncome;
        return money;
    }

    public double getSponsor() {
        return sponsor;
    }

    public double getVenueCost() {
        return venueCost;
    }

    public double getPlayerSalary() {
        return playerSalary;
    }

    public double getTicketIncome() {
        return ticketIncome;
    }

}
