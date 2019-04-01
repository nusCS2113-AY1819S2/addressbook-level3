
package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

public class Doctor {
    //@@author matthiaslum
    public static final String EXAMPLE = "John Doe";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Doctor's names should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String doctorName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given doctor string is invalid.
     */

    public Doctor(String doctor) throws IllegalValueException {
        doctor = doctor.trim();
        if (!isValidName(doctor)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.doctorName = doctor;
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
    public List<String> getWordsInName() {
        return Arrays.asList(doctorName.split("\\s+"));
    }
    //@@author

    //@@author shawn-t

    // to change doctor name
    public void ReferTo (String newDoctor) throws IllegalValueException {
        newDoctor = newDoctor.trim();
        if (!isValidName(newDoctor)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.doctorName = newDoctor;
    }
    //@@author

    //@@author matthiaslum
    @Override
    public String toString() {
        return doctorName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Doctor // instanceof handles nulls
                && this.doctorName.equals(((Doctor) other).doctorName)); // state check
    }

    @Override
    public int hashCode() {
        return doctorName.hashCode();
    }
    //@@author
}

