package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Name;

/**
 * JAXB-friendly adapted name data holder class.
 */
public class AdaptedName {

    @XmlValue
    private String playerName;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedName() {}

    /**
     * Converts a given Name into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedName
     */
    public AdaptedName(Name source) {
        playerName = source.fullName;
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
        return Utils.isAnyNull(playerName);
    }

    /**
     * Converts this jaxb-friendly adapted name object into the Name object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public Name toModelType() throws IllegalValueException {
        return new Name(playerName);
    }
}
