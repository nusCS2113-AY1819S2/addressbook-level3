package seedu.addressbook.data.player;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's health status in the address book.
 */
public class HealthStatus {
    public static final String EXAMPLE = "Healthy";
    public static final String MESSAGE_HEALTHSTATUS_CONSTRAINTS = "Player's Health"
            + "Status should be spaces or alphanumeric characters";
    public static final String HEALTHSTATUS_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullHs;

    /**
     * Validates given Health Status.
     *
     * @throws IllegalValueException if given Health Status string is invalid.
     */
    public HealthStatus (String hs) throws IllegalValueException {
        hs = hs.trim();
        if (!isValidHs(hs)) {
            throw new IllegalValueException(MESSAGE_HEALTHSTATUS_CONSTRAINTS);
        }
        this.fullHs = hs;
    }

    /**
     * Returns true if a given string is a valid Health Strategy.
     */
    public static boolean isValidHs(String test) {
        return test.matches(HEALTHSTATUS_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in Health Status, in order.
     */
    public List<String> getWordsInTeam() {
        return Arrays.asList(fullHs.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullHs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthStatus // instanceof handles nulls
                && this.fullHs.equals(((HealthStatus) other).fullHs)); // state check
    }

    @Override
    public int hashCode() {
        return fullHs.hashCode();
    }

}
