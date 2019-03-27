package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */

public class Age {

    public static final String EXAMPLE = "20";
    public static final String MESSAGE_AGE_CONSTRAINTS = "Age of a player must be an integer";
    public static final String AGE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given age.
     *
     * @throws IllegalValueException if given age integer is invalid.
     */

    public Age (String age) throws IllegalValueException {
        age = age.trim();

        if (!isValidAge(age)) {
            throw new IllegalValueException(MESSAGE_AGE_CONSTRAINTS);
        }
        this.value = age;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        int temp = Integer.parseInt(test);
        return (test.matches(AGE_VALIDATION_REGEX)&& temp > 0 && temp < 100);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age// instanceof handles nulls
                && this.value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
