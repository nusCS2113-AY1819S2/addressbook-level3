package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

public class JerseyNumber {

    public static final int example = 10;
    public static final String MESSAGE_JN_CONSTRAINTS = "Jersey Number of a player must be an integer";


    public final int fullJN;
    /**
     * Validates given jersey number.
     *
     * @throws IllegalValueException if given Jersey Number integer is invalid.
     */

    public JerseyNumber (int JN) throws IllegalValueException {
        if (!isValidJN(JN)) {
            throw new IllegalValueException(MESSAGE_JN_CONSTRAINTS);
        }
        this.fullJN = JN;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidJN(int test) {
        return (test>0 && test <35);
    }


}