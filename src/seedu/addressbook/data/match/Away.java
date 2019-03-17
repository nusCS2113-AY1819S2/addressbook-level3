package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Match away team in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAway(String)}
 */
public class Away {

    public static final String EXAMPLE = "Huddersfield";
    public static final String MESSAGE_AWAY_CONSTRAINTS = "Match away teams should be spaces or alphanumeric characters";
    public static final String AWAY_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullAway;

    /**
     * Validates given away team.
     *
     * @throws IllegalValueException if given away team string is invalid.
     */
    public Away(String away) throws IllegalValueException {
        away = away.trim();
        if (!isValidAway(away)) {
            throw new IllegalValueException(MESSAGE_AWAY_CONSTRAINTS);
        }
        this.fullAway = away;
    }

    /**
     * Returns true if a given string is a valid match away team.
     */
    public static boolean isValidAway(String test) {
        return test.matches(AWAY_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the away team, in order.
     */
    public List<String> getWordsInAway() {
        return Arrays.asList(fullAway.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullAway;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Away // instanceof handles nulls
                && this.fullAway.equals(((Away) other).fullAway)); // state check
    }

    @Override
    public int hashCode() {
        return fullAway.hashCode();
    }

}