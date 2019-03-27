package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's jersey number on the field.
 */
public class JerseyNumber {

    public static final String EXAMPLE = "10";
    public static final String MESSAGE_JN_CONSTRAINTS = "Jersey Number of a player must be an integer";
    public static final String JN_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given jersey number.
     *
     * @throws IllegalValueException if given Jersey Number integer is invalid.
     */

    public JerseyNumber (String jn) throws IllegalValueException {

        jn = jn.trim();

        if (!isValidJn(jn)) {
            throw new IllegalValueException(MESSAGE_JN_CONSTRAINTS);
        }
        this.value = jn;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidJn(String test) {
        int temp = Integer.parseInt(test);
        return (test.matches(JN_VALIDATION_REGEX)&& temp >= 0 && temp < 35);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JerseyNumber // instanceof handles nulls
                && this.value.equals(((JerseyNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
