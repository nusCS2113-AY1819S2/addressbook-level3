package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents country of a player made in the address book.
 */

public class Country implements Comparable<Country>{
    public static final String EXAMPLE = "Spain";
    public static final String MESSAGE_COUNTRY_CONSTRAINTS = "Country name should be a string";
    public static final String COUNTRY_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullCountry;

    /**
     * Validates given country name.
     *
     * @throws IllegalValueException if given country string is invalid.
     */

    public Country(String name) throws IllegalValueException {
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_COUNTRY_CONSTRAINTS);
        }
        this.fullCountry = name;
    }


    /**
     * Returns true if a given string is a valid country name.
     */
    public static boolean isValidName(String test) {
        return test.matches(COUNTRY_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInCountry() {
        return Arrays.asList(fullCountry.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullCountry;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Country // instanceof handles nulls
                && this.fullCountry.equals(((Country) other).fullCountry)); // state check
    }

    @Override
    public int hashCode() {
        return fullCountry.hashCode();
    }

    @Override
    public int compareTo (Country country) {
        return this.fullCountry.compareToIgnoreCase(country.fullCountry);
    }

}



