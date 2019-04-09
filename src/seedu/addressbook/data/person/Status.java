package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

//@@author WuPeiHsuan

public class Status implements Comparable<Status>{

    public static final String EXAMPLE = "Observation";
    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status should be Observation/ Critical / Waiting for Surgery " +
            "/ Therapy / Life Support / Waiting for doctor appointment";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static final String OBSERVATION = "Observation";
    public static final String THERAPY = "Therapy";
    public static final String SURGERY = "Waiting for Surgery";
    public static final String LIFE_SUPPORT = "Life Support";
    public static final String CRITICAL = "Critical";
    public static final String APPOINTMENT = "Waiting for doctor appointment";

    public final String status;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given status string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        status = status.trim();
        if (!isValidStatus(status)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
        if(!isCorrectStatus(status)){
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }

        this.status = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isCorrectStatus(String test) {
        switch (test){
            case OBSERVATION:
            case THERAPY:
            case SURGERY:
            case LIFE_SUPPORT:
            case CRITICAL:
            case APPOINTMENT:
                return true;
            default:
                return false;
        }
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