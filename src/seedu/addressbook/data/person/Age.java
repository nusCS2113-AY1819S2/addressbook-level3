package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(int)}
 */

public class Age {

    public static final int example = 20;
    public static final String MESSAGE_AGE_CONSTRAINTS = "Age of a player must be an integer";


    public final int fullAge;
    /**
     * Validates given age.
     *
     * @throws IllegalValueException if given age integer is invalid.
     */

    public Age (int age) throws IllegalValueException {
        if (!isValidAge(age)) {
            throw new IllegalValueException(MESSAGE_AGE_CONSTRAINTS);
        }
        this.fullAge = age;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(int test) {
        return (test>0 && test <100);
    }




}
