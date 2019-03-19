package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's jersey number on the field.
 */
public class JerseyNumber {

    public static final int EXAMPLE = 10;
    public static final String MESSAGE_JN_CONSTRAINTS = "Jersey Number of a player must be an integer";


    public final int fullJn;
    /**
     * Validates given jersey number.
     *
     * @throws IllegalValueException if given Jersey Number integer is invalid.
     */

    public JerseyNumber (int jn) throws IllegalValueException {
        if (!isValidJn(jn)) {
            throw new IllegalValueException(MESSAGE_JN_CONSTRAINTS);
        }
        this.fullJn = jn;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidJn(int test) {
        return (test > 0 && test < 35);
    }

}
