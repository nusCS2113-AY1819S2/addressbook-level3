package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents goals scored by a player made in the address book.
 */
public class GoalsScored {

    public static final int EXAMPLE = 10;
    public static final String MESSAGE_GS_CONSTRAINTS = "No.of goals scored for a player must be an integer";

    public final int fullGs;
    /**
     * Validates given goals scored.
     *
     * @throws IllegalValueException if given goals scored integer is invalid.
     */

    public GoalsScored (int gs) throws IllegalValueException {
        if (!isValidGs(gs)) {
            throw new IllegalValueException(MESSAGE_GS_CONSTRAINTS);
        }
        this.fullGs = gs;
    }

    /**
     * Returns true if a given integer is a valid goals scored number.
     */
    public static boolean isValidGs(int test) {
        return (test >= 0 && test < 100);
    }

}
