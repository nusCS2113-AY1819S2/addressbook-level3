package seedu.addressbook.data.team;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Team's country in the address book.
 */

public class Country {

    public static final String EXAMPLE = "Singapore";
    public static final String MESSAGE_COUNTRY_CONSTRAINTS = "Country names should be alphanumeric";
    public static final String COUNTRY_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String value;

    /**
     * Validates given country names.
     */
    public Country(String country) throws IllegalValueException {
        country = country.trim();
        if (!isValidCountry(country)) {
            throw new IllegalValueException(MESSAGE_COUNTRY_CONSTRAINTS);
        }
        this.value = country;
    }

    /**
     * Checks if a given string is a valid country names.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(COUNTRY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Country // instanceof handles nulls
                && this.value.equals(((Country) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
