package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents goals assisted by a player made in the address book.
 */
public class GoalsAssisted {

    public static final int EXAMPLE = 10;
    public static final String MESSAGE_GA_CONSTRAINTS = "No.of assists for a player must be an integer";

    public final int fullGa;
    /**
     * Validates given No. of assists.
     *
     * @throws IllegalValueException if given assist number integer is invalid.
     */

    public GoalsAssisted (int ga) throws IllegalValueException {
        if (!isValidGa(ga)) {
            throw new IllegalValueException(MESSAGE_GA_CONSTRAINTS);
        }
        this.fullGa = ga;
    }

    /**
     * Returns true if a given integer is a valid goals assisted number.
     */

    public static boolean isValidGa(int test) {
        return (test >= 0 && test < 100);
    }

}
