package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

public class GoalsAssisted {

    public static final int example = 10;
    public static final String MESSAGE_GA_CONSTRAINTS = "No.of assists for a player must be an integer";

    public final int fullGA;
    /**
     * Validates given No. of assists.
     *
     * @throws IllegalValueException if given assist number integer is invalid.
     */

    public GoalsAssisted (int GA) throws IllegalValueException {
        if (!isValidGA(GA)) {
            throw new IllegalValueException(MESSAGE_GA_CONSTRAINTS);
        }
        this.fullGA = GA;
    }

    /**
     * Returns true if a given integer is a valid goals assisted number.
     */

    public static boolean isValidGA(int test) {
        return (test>=0 && test <100);
    }

}