package seedu.addressbook.data.player;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents gender of a player made in the address book.
 */
public class Gender {
    public static final String EXAMPLE = "Male";
    public static final String MESSAGE_GENDER_CONSTRAINTS = "Player's Gender"
            + "should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullGender;

    /**
     * Validates given gender.
     *
     * @throws IllegalValueException if given gender string is invalid.
     */
    public Gender (String gender) throws IllegalValueException {
        gender = gender.trim();
        if (!isValidGender(gender)) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }
        this.fullGender = gender;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the Gender, in order.
     */
    public List<String> getWordsInName() {
        return Arrays.asList(fullGender.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullGender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.fullGender.equals(((Gender) other).fullGender)); // state check
    }

    @Override
    public int hashCode() {
        return fullGender.hashCode();
    }

}
