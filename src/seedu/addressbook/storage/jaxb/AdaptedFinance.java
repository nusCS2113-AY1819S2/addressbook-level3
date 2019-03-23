package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.finance.ReadOnlyFinance;


public class AdaptedFinance {

    @XmlElement(required = true)
    private String teamName;
    @XmlElement(required = true)
    private String histogram;
    @XmlElement(required = true)
    private double money;


    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedFinance() {}


    /**
     * Converts a given match into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedMatch
     */
    public AdaptedFinance(ReadOnlyFinance source) {
        teamName = source.getTeamName();

        histogram = source.getHistogramString();

        money = source.getFinance();
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
        return Utils.isAnyNull(teamName, histogram, money);
    }

    /**
     * Converts this jaxb-friendly adapted match object into the match object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public Finance toModelType() throws IllegalValueException {
        final String teamName = this.teamName;
        final String histogram = this.histogram;
        final double money = this.money;
        return new Finance(teamName, histogram, money);
    }
}


