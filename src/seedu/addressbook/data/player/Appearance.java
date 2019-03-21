package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents number of appearances a player made in the address book.
 */

public class Appearance {

    public static final int EXAMPLE = 30;
    public static final String MESSAGE_APPEARANCE_CONSTRAINTS = "No. of appearance of a player must be an integer";


    public final int fullAppearance;
    /**
     * Validates given appearance number.
     *
     * @throws IllegalValueException if given Number of Appearance integer is invalid.
     */

    public Appearance (int appearance) throws IllegalValueException {
        if (!isValidApp(appearance)) {
            throw new IllegalValueException(MESSAGE_APPEARANCE_CONSTRAINTS);
        }
        this.fullAppearance = appearance;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidApp(int test) {
        return (test > 0 && test < 35);
    }
}
