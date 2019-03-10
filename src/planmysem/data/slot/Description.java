package planmysem.data.slot;

import planmysem.data.exception.IllegalValueException;

/**
 * Represents a Slot's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Description {
    private static final String EXAMPLE = "Meeting with John Doe regarding CS2113T project.";
    private static final String MESSAGE_CONSTRAINTS =
            "Slot's description should be spaces or alphanumeric characters";
    private static final String VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value string is invalid.
     */
    public Description(String value) throws IllegalValueException {
        if (value != null && !isValid(value)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid Slot description.
     */
    public static boolean isValid(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
