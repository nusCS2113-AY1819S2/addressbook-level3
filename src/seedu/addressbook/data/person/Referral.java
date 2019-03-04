package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

public class Referral {

    public static final String EXAMPLE = "Dr. John";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Person names should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String doctorName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Referral(String name) throws IllegalValueException {
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.doctorName = name;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
//    public List<String> getWordsInName() {
//        return Arrays.asList(doctorName.split("\\s+"));
//    }

    @Override
    public String toString() {
        return doctorName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.doctorName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return doctorName.hashCode();
    }
}
