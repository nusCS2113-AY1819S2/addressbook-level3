package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents number of appearances a player made in the address book.
 */

public class Appearance {

    public static final String EXAMPLE = "30";
    public static final String MESSAGE_APPEARANCE_CONSTRAINTS = "No.of appearance must be a non-negative integer";
    public static final String APPEARANCE_VALIDATION_REGEX = "\\d+";


    public final String value;
    /**
     * Validates given appearance number.
     *
     * @throws IllegalValueException if given Number of Appearance integer is invalid.
     */

    public Appearance (String appearance) throws IllegalValueException {
        appearance = appearance.trim();
        if (!isValidApp(appearance)) {
            throw new IllegalValueException(MESSAGE_APPEARANCE_CONSTRAINTS);
        }
        this.value = appearance;
    }

    /**
     * Returns true if a given integer is a valid jersey number.
     */
    public static boolean isValidApp(String test) {
        try {
            int temp = Integer.parseInt(test);
            return (test.matches(APPEARANCE_VALIDATION_REGEX) && temp >= 0);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appearance // instanceof handles nulls
                && this.value.equals(((Appearance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
