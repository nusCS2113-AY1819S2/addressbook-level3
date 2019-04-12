package seedu.addressbook.storage.jaxb;

import java.text.ParseException;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.Finance;

/**
 * JAXB-friendly adapted team data holder class.
 */
public class AdaptedFinance {
    @XmlElement(required = true)
    private String teamName;
    @XmlElement(required = true)
    private double sponsorMoney;
    @XmlElement(required = true)
    private double ticketIncome;
    @XmlElement(required = true)
    private double quarterOne;
    @XmlElement(required = true)
    private double quarterTwo;
    @XmlElement(required = true)
    private double quarterThree;
    @XmlElement(required = true)
    private double quarterFour;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedFinance() {}


    /**
     * Converts a given finance into this class for JAXB use.
     */
    public AdaptedFinance(ReadOnlyFinance source) {
        teamName = source.getTeamName();
        sponsorMoney = source.getSponsor();
        ticketIncome = source.getTicketIncome();
        quarterOne = source.getQuarterOne();
        quarterTwo = source.getQuarterTwo();
        quarterThree = source.getQuarterThree();
        quarterFour = source.getQuarterFour();
    }

    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        return Utils.isAnyNull(teamName);
    }

    /**
     * Converts this jaxb-friendly adapted finance object into the finance object.
     */
    public Finance toModelType() throws IllegalValueException, ParseException {
        final String teamName = this.teamName;
        final double sponsorMoney = this.sponsorMoney;
        final double ticketIncome = this.ticketIncome;
        final double quarterOne = this.quarterOne;
        final double quarterTwo = this.quarterTwo;
        final double quarterThree = this.quarterThree;
        final double quarterFour = this.quarterFour;
        return new Finance(teamName, sponsorMoney, ticketIncome, quarterOne, quarterTwo, quarterThree, quarterFour);
    }
}
