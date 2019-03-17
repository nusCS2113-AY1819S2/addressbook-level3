package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

public class HealthStatus {
    public static final String EXAMPLE = "Healthy";
    public static final String MESSAGE_HEALTHSTATUS_CONSTRAINTS = "Player's Health Status should be spaces or alphanumeric characters";
    public static final String HEALTHSTATUS_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullHS;

    /**
     * Validates given Health Status.
     *
     * @throws IllegalValueException if given Health Status string is invalid.
     */
    public HealthStatus (String HS) throws IllegalValueException {
        HS = HS.trim();
        if (!isValidHS(HS)) {
            throw new IllegalValueException(MESSAGE_HEALTHSTATUS_CONSTRAINTS);
        }
        this.fullHS = HS;
    }

    /**
     * Returns true if a given string is a valid Health Strategy.
     */
    public static boolean isValidHS(String test) {
        return test.matches(HEALTHSTATUS_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in Health Status, in order.
     */
    public List<String> getWordsInTeam() {
        return Arrays.asList(fullHS.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullHS;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthStatus // instanceof handles nulls
                && this.fullHS.equals(((HealthStatus) other).fullHS)); // state check
    }

    @Override
    public int hashCode() {
        return fullHS.hashCode();
    }

}
