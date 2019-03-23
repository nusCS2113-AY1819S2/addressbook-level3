package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

//@@author WuPeiHsuan

public class Status implements Comparable<Status>{

    public static final String EXAMPLE = "Observation";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Status should be Observation/ Critical / Waiting for Surgery " +
            "/ Therapy / Life-support / Waiting for doctor appointment";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String status;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given status string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        status = status.trim();
        if (!isValidStatus(status)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }




    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    /**compare status for sorting function*/

    @Override
    public int compareTo(Status status) {
        return this.status.compareTo(status.status);
    }

}
//@@author