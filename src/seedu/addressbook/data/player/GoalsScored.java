package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

public class GoalsScored {

    public static final int example = 10;
    public static final String MESSAGE_GS_CONSTRAINTS = "No.of goals scored for a player must be an integer";

    public final int fullGS;
    /**
     * Validates given goals scored.
     *
     * @throws IllegalValueException if given goals scored integer is invalid.
     */

    public GoalsScored (int GS) throws IllegalValueException {
        if (!isValidGS(GS)) {
            throw new IllegalValueException(MESSAGE_GS_CONSTRAINTS);
        }
        this.fullGS = GS;
    }

    /**
     * Returns true if a given integer is a valid goals scored number.
     */
    public static boolean isValidGS(int test) {
        return (test>=0 && test <100);
    }

}
