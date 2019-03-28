package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents goals scored by a player made in the address book.
 */
public class GoalsScored {

    public static final String EXAMPLE = "1";
    public static final String MESSAGE_GS_CONSTRAINTS = "No.of goals scored for a player must be a non-negative integer";
    public static final String GS_VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Validates given goals scored.
     *
     * @throws IllegalValueException if given goals scored integer is invalid.
     */

    public GoalsScored (String gs) throws IllegalValueException {
        gs = gs.trim();
        if (!isValidGs(gs)) {
            throw new IllegalValueException(MESSAGE_GS_CONSTRAINTS);
        }
        this.value = gs;
    }

    /**
     * Returns true if a given integer is a valid goals scored number.
     */
    public static boolean isValidGs(String test)  {

        try {
            int temp = Integer.parseInt(test);
            return (test.matches(GS_VALIDATION_REGEX)&& temp >= 0);
        } catch(NumberFormatException nfe){
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
                || (other instanceof GoalsScored // instanceof handles nulls
                && this.value.equals(((GoalsScored) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
