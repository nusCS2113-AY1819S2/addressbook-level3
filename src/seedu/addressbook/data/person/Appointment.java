package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

public class Appointment {

    public static final String EXAMPLE = "30November";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Appointment dates should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String appointmentDate;

    /**
     * Validates given appointment.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Appointment(String date) throws IllegalValueException {
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.appointmentDate = date;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInDate() {
        return Arrays.asList(appointmentDate.split("\\s+"));
    }

    @Override
    public String toString() {
        return appointmentDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.appointmentDate.equals(((Appointment) other).appointmentDate)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentDate.hashCode();
    }

}