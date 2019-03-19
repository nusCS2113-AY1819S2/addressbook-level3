package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Match date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String EXAMPLE = "17 MAR 2019";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Match dates should be spaces or alphanumeric characters";
    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullDate;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.fullDate = date;
    }

    /**
     * Returns true if a given string is a valid match date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the date, in order.
     */
    public List<String> getWordsInDate() {
        return Arrays.asList(fullDate.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.fullDate.equals(((Date) other).fullDate)); // state check
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }

}