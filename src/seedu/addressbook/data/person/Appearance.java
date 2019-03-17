package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

public class Appearance {

    public static final int example = 30;
    public static final String MESSAGE_APPEARANCE_CONSTRAINTS = "No. of appearance of a player must be an integer";


    public final int fullAppearance;
    /**
     * Validates given appearance number.
     *
     * @throws IllegalValueException if given Number of Appearance integer is invalid.
     */

    public Appearance (int Appearance) throws IllegalValueException {
        if (!isValidAPP(Appearance)) {
            throw new IllegalValueException(MESSAGE_APPEARANCE_CONSTRAINTS);
        }
        this.fullAppearance = Appearance;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidAPP(int test) {
        return (test>0 && test <35);
    }


}
