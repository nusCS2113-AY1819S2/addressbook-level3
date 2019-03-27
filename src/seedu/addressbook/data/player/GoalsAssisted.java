package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents goals assisted by a player made in the address book.
 */
public class GoalsAssisted {

    public static final String EXAMPLE = "1";
    public static final String MESSAGE_GA_CONSTRAINTS = "No.of assists for a player must be an integer";
    public static final String GA_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given No. of assists.
     *
     * @throws IllegalValueException if given assist number integer is invalid.
     */

    public GoalsAssisted (String ga) throws IllegalValueException {
        ga = ga.trim();
        if (!isValidGa(ga)) {
            throw new IllegalValueException(MESSAGE_GA_CONSTRAINTS);
        }
        this.value = ga;
    }

    /**
     * Returns true if a given integer is a valid goals assisted number.
     */

    public static boolean isValidGa(String test) {
        int temp = Integer.parseInt(test);
        return (test.matches(GA_VALIDATION_REGEX)&& temp >= 0 && temp < 100);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalsAssisted // instanceof handles nulls
                && this.value.equals(((GoalsAssisted) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
