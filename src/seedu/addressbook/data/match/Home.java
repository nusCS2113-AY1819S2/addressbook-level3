package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Match home team in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHome(String)}
 */
public class Home {

    public static final String EXAMPLE = "West Ham";
    public static final String MESSAGE_HOME_CONSTRAINTS = "Match home teams should be spaces or alphanumeric characters";
    public static final String HOME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullHome;

    /**
     * Validates given home team.
     *
     * @throws IllegalValueException if given home team string is invalid.
     */
    public Home(String home) throws IllegalValueException {
        home = home.trim();
        if (!isValidHome(home)) {
            throw new IllegalValueException(MESSAGE_HOME_CONSTRAINTS);
        }
        this.fullHome = home;
    }

    /**
     * Returns true if a given string is a valid match home team.
     */
    public static boolean isValidHome(String test) {
        return test.matches(HOME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the home team, in order.
     */
    public List<String> getWordsInHome() {
        return Arrays.asList(fullHome.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullHome;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Home // instanceof handles nulls
                && this.fullHome.equals(((Home) other).fullHome)); // state check
    }

    @Override
    public int hashCode() {
        return fullHome.hashCode();
    }

}